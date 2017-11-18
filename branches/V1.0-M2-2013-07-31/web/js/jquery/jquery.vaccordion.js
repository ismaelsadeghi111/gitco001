(function(jQuery) {

	// cache some values
	var cache	= {
			idx_expanded	: -1, // the index of the current expanded slice
			sliceH			: 0,  // the default slice's height	
			current			: 0,  // controls the current slider position
			totalSlices		: 0	  // total number of slices
		},
		aux		= {
			// triggered when we click a slice. If the slice is expanded,
			// we close it, otherwise we open it..
			selectSlice		: function( jQueryel, jQueryslices, jQuerynavNext, jQuerynavPrev, settings ) {
				
				return jQuery.Deferred(
					function( dfd ) {
					
						var	expanded	= jQueryel.data('expanded'),
							pos			= jQueryel.data('position'),
							
							itemHeight, othersHeight,
							
							jQueryothers		= jQueryslices.not( jQueryel );
							
						// if it's opened..	
						if( expanded ) {
							jQueryel.data( 'expanded', false );
							cache.idx_expanded	= -1;
							
							// the default values of each slices's height
							//itemHeight	= cache.sliceH;
							//othersHeight= cache.sliceH;
							
							// hide the content div
							jQueryel.find('.va-content').hide();
							
							// control the navigation buttons visibility
							if( aux.canSlideUp( jQueryslices, settings ) )
								jQuerynavPrev.fadeIn();
							else
								jQuerynavPrev.fadeOut();
								
							if( aux.canSlideDown( jQueryslices, settings ) )
								jQuerynavNext.fadeIn();
							else
								jQuerynavNext.fadeOut();
						}
						// if it's closed..
						else {
							jQueryel.data( 'expanded', false );
							cache.idx_expanded	= jQueryel.index();
							jQueryothers.data( 'expanded', false );
							// the current slice's height
							itemHeight	= settings.expandedHeight;
							// the height the other slices will have
							othersHeight= Math.ceil( ( settings.accordionH - settings.expandedHeight ) / ( settings.visibleSlices - 1 ) );
							
							// control the navigation buttons visibility
							if( cache.idx_expanded > 0 )
								jQuerynavPrev.fadeIn();
							else	
								jQuerynavPrev.fadeOut();
							
							if( cache.idx_expanded < cache.totalSlices - 1 )
								jQuerynavNext.fadeIn();	
							else
								jQuerynavNext.fadeOut();
						}
						
						// the animation parameters for the clicked slice
						var	animParam	= { 
											height	: itemHeight + 'px', 
											opacity : 1,
											top		: ( pos - 1 ) * othersHeight + 'px'
										  };
						
						// animate the clicked slice and also its title (<h3>)
						jQueryel.stop()
						   .animate( animParam, settings.animSpeed, settings.animEasing, function() {
								if( !expanded )
									jQueryel.find('.va-content').fadeIn( settings.contentAnimSpeed );
						   })
						   .find('.va-title')
						   .stop()
						   .animate({
								lineHeight	: cache.sliceH + 'px'
						   }, settings.animSpeed, settings.animEasing );	
						   
						// animate all the others
						jQueryothers.each(function(i){
							var jQueryother	= jQuery(this),
								posother= jQueryother.data('position'),
								t;
							
							if( expanded )
								t	= ( posother - 1 ) * othersHeight ;
							else {
								if( posother < pos )
									t	= ( posother - 1 ) * othersHeight ;
								else
									t	= ( ( posother - 2 ) * othersHeight ) + settings.expandedHeight;
							}
							
							jQueryother.stop()
								  .animate( {
										top		: t + 'px',
										height	: othersHeight + 'px',
										opacity	: ( expanded ) ? 1 : settings.animOpacity
								  }, settings.animSpeed, settings.animEasing, dfd.resolve )
								  .find('.va-title')
								  .stop()
								  .animate({
										lineHeight	: othersHeight + 'px'
								  }, settings.animSpeed, settings.animEasing )
								  .end()
								  .find('.va-content')
								  .hide();
						});
					}
				).promise();
				
			},
			// triggered when clicking the navigation buttons / mouse scrolling
			navigate : function( dir, jQueryslices, jQuerynavNext, jQuerynavPrev, settings ) {
				// if animating return
				if( jQueryslices.is(':animated') ) 
					return false;
				
				// all move up / down one position
				// if settings.savePositions is false, then we need to close any expanded slice before sliding
				// otherwise we slide, and the next one will open automatically
				var jQueryel;
				
				if( cache.idx_expanded != -1 && !settings.savePositions ) {
					jQueryel	= jQueryslices.eq( cache.idx_expanded );
					
					jQuery.when( aux.selectSlice( jQueryel, jQueryslices, jQuerynavNext, jQuerynavPrev, settings ) ).done(function(){
						setTimeout(function() {
						aux.slide( dir, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
						}, 10);
					});
				}
				else {
					aux.slide( dir, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
				}	
			},
			slide : function( dir, jQueryslices, jQuerynavNext, jQuerynavPrev, settings ) {
				// control if we can navigate.
				// control the navigation buttons visibility.
				// the navigation will behave differently for the cases we have all the slices closed, 
				// and when one is opened. It will also depend on settings.savePositions 
				if( cache.idx_expanded === -1 || !settings.savePositions ) {
				if( dir === 1 && cache.current + settings.visibleSlices >= cache.totalSlices )
					return false;
				else if( dir === -1 && cache.current === 0 )
					return false;
				
				if( dir === -1 && cache.current === 1 )
					jQuerynavPrev.fadeOut();
				else
					jQuerynavPrev.fadeIn();
					
					if( dir === 1 && cache.current + settings.visibleSlices === cache.totalSlices - 1 )
					jQuerynavNext.fadeOut();
				else
					jQuerynavNext.fadeIn();
				}
				else {
					if( dir === 1 && cache.idx_expanded === cache.totalSlices - 1 )
						return false;
					else if( dir === -1 && cache.idx_expanded === 0 )
						return false;
						
					if( dir === -1 && cache.idx_expanded === 1 )
						jQuerynavPrev.fadeOut();
					else
						jQuerynavPrev.fadeIn();
						
					if( dir === 1 && cache.idx_expanded === cache.totalSlices - 2 )
						jQuerynavNext.fadeOut();
					else
						jQuerynavNext.fadeIn();
				}
				
				var jQuerycurrentSlice	= jQueryslices.eq( cache.idx_expanded ),
					jQuerynextSlice,
					t;
				
				( dir === 1 ) ? jQuerynextSlice = jQuerycurrentSlice.next() : jQuerynextSlice = jQuerycurrentSlice.prev();
				
				// if we cannot slide up / down, then we just call the selectSlice for the previous / next slice
				if( ( dir === 1 && !aux.canSlideDown( jQueryslices, settings ) ) || 
					( dir === -1 && !aux.canSlideUp( jQueryslices, settings ) ) ) {
					aux.selectSlice( jQuerynextSlice, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
					return false;
				}
					
				// if we slide down, the top and position of each slice will decrease
				if( dir === 1 ) {
					cache.current++;
					t = '-=' + cache.sliceH;
					pos_increment	= -1;
				}
				else {
					cache.current--;
					t = '+=' + cache.sliceH;
					pos_increment	= 1;
				}
				
				jQueryslices.each(function(i) {
					var jQueryslice		= jQuery(this),
						pos			= jQueryslice.data('position');
					
					// all closed or savePositions is false
					if( !settings.savePositions || cache.idx_expanded === -1 )
						jQueryslice.stop().animate({top : t}, settings.animSpeed, settings.animEasing);
					else {
						var itemHeight, othersHeight;
						
						// if the slice is the one we should open..
						if( i === jQuerynextSlice.index() ) {
							jQueryslice.data( 'expanded', true );
							cache.idx_expanded	= jQueryslice.index();
							itemHeight			= settings.expandedHeight;
							othersHeight		= ( settings.accordionH - settings.expandedHeight ) / ( settings.visibleSlices - 1 );
							
							jQueryslice.stop()
						          .animate({
										height		: itemHeight + 'px', 
										opacity 	: 1,
										top			: ( dir === 1 ) ? ( pos - 2 ) * othersHeight + 'px' : pos * othersHeight + 'px'
								  }, settings.animSpeed, settings.animEasing, function() {
										jQueryslice.find('.va-content').fadeIn( settings.contentAnimSpeed );
								  })
								  .find('.va-title')
								  .stop()
								  .animate({
										lineHeight	: cache.sliceH + 'px'
								  }, settings.animSpeed, settings.animEasing );
						}
						// if the slice is the one opened, lets close it
						else if( jQueryslice.data('expanded') ){
							// collapse
							
							jQueryslice.data( 'expanded', false );
							othersHeight		= ( settings.accordionH - settings.expandedHeight ) / ( settings.visibleSlices - 1 );
							
							jQueryslice.stop()
						          .animate({ 
										height	: othersHeight + 'px', 
										opacity : settings.animOpacity,
										top		: ( dir === 1 ) ? '-=' + othersHeight : '+=' + settings.expandedHeight
								  }, settings.animSpeed, settings.animEasing )
								  .find('.va-title')
								  .stop()
								  .animate({
										lineHeight	: othersHeight + 'px'
								  }, settings.animSpeed, settings.animEasing )
								  .end()
								  .find('.va-content')
								  .hide();		  
						}
						// all the others..
						else {
							jQueryslice.data( 'expanded', false );
							othersHeight		= ( settings.accordionH - settings.expandedHeight ) / ( settings.visibleSlices - 1 );
							
							jQueryslice.stop()
						          .animate({ 
										top		: ( dir === 1 ) ? '-=' + othersHeight : '+=' + othersHeight
								  }, settings.animSpeed, settings.animEasing );
						}
					}
					// change the slice's position
					jQueryslice.data().position += pos_increment;
				});
			},
			canSlideUp		: function( jQueryslices, settings ) {
				var jQueryfirst			= jQueryslices.eq( cache.current );
					
				if( jQueryfirst.index() !== 0 )
					return true;
			},
			canSlideDown	: function( jQueryslices, settings ) {
				var jQuerylast			= jQueryslices.eq( cache.current + settings.visibleSlices - 1 );
					
				if( jQuerylast.index() !== cache.totalSlices - 1 )
					return true;
			}
		},
		methods = {
			init 		: function( options ) {
				
				if( this.length ) {
					
					var settings = {
						// the accordion's width
						accordionW		: 225,
						// the accordion's height
						accordionH		: 450,
						// number of visible slices
						visibleSlices	: 3,
						// the height of a opened slice
						// should not be more than accordionH
						expandedHeight	: 0,
						// speed when opening / closing a slice
						animSpeed		: 250,
						// easing when opening / closing a slice
						animEasing		: 'jswing',
						// opacity value for the collapsed slices
						animOpacity		: 0.2,
						// time to fade in the slice's content
						contentAnimSpeed: 900,
						// if this is set to false, then before
						// sliding we collapse any opened slice
						savePositions	: true
					};
					
					return this.each(function() {
						
						// if options exist, lets merge them with our default settings
						if ( options ) {
							jQuery.extend( settings, options );
						}
						
						var jQueryel 			= jQuery(this),
							// the accordion's slices
							jQueryslices			= jQueryel.find('div.va-slice'),
							// the navigation buttons
							jQuerynavNext		= jQueryel.find('span.va-nav-next'),
							jQuerynavPrev		= jQueryel.find('span.va-nav-prev');
							
						// each slice's height
						cache.sliceH		= Math.ceil( settings.accordionH / settings.visibleSlices );
						
						// total slices
						cache.totalSlices	= jQueryslices.length;
						
						// control some user config parameters
						if( settings.expandedHeight > settings.accordionH )
							settings.expandedHeight = settings.accordionH;
						else if( settings.expandedHeight <= cache.sliceH )
							settings.expandedHeight = cache.sliceH + 50; // give it a minimum
							
						// set the accordion's width & height
						jQueryel.css({
							width	: settings.accordionW + 'px',
//							height	: settings.accordionH + 'px'
							height	: 500 + 'px'
						});
						
						// show / hide jQuerynavNext 
						if( settings.visibleSlices < cache.totalSlices  )
							jQuerynavNext.show();
						
						// set the top & height for each slice.
						// also save the position of each one.
						// as we navigate, the first one in the accordion
						// will have position 1 and the last settings.visibleSlices.
						// finally set line-height of the title (<h3>)
						jQueryslices.each(function(i){
							var jQueryslice	= jQuery(this);
							jQueryslice.css({
								/*top		: i * cache.sliceH + 'px',
								height	: cache.sliceH + 'px'*/
                                top		: 10 + 'px',
								height	: cache.sliceH + 'px'
							}).data( 'position', (i + 1) );
						})
						.children('.va-title')
						.css( 'line-height', cache.sliceH + 'px' );
						
						// click event
						jQueryslices.bind('click.vaccordion', function(e) {
							// only if we have more than 1 visible slice. 
							// otherwise we will just be able to slide.
							if( settings.visibleSlices > 1 ) {
								var jQueryel			= jQuery(this);
								aux.selectSlice( jQueryel, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
							}
						});
						
						// navigation events
						jQuerynavNext.bind('click.vaccordion', function(e){
							aux.navigate( 1, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
						});
						
						jQuerynavPrev.bind('click.vaccordion', function(e){
							aux.navigate( -1, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
						});
						
						// adds events to the mouse
						jQueryel.bind('mousewheel.vaccordion', function(e, delta) {
							if(delta > 0) {
								aux.navigate( -1, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
							}	
							else {
								aux.navigate( 1, jQueryslices, jQuerynavNext, jQuerynavPrev, settings );
							}	
							return false;
						});
						
					});
				}
			}
		};
	
	jQuery.fn.vaccordion = function(method) {
		if ( methods[method] ) {
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'object' || ! method ) {
			return methods.init.apply( this, arguments );
		} else {
			jQuery.error( 'Method ' +  method + ' does not exist on jQuery.vaccordion' );
		}
	};
	
})(jQuery);