USE [DblCentral]
GO

/****** Object:  Table [dbo].[catering]    Script Date: 02/25/2014 10:06:19 Þ.Ù ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[catering](
	[catering_id] [int] IDENTITY(1,1) NOT NULL,
	[title_en] [nvarchar](50) NULL,
	[title_fr] [nvarchar](50) NULL,
	[image_url] [nvarchar](255) NULL,
	[min_serving] [nvarchar](2) NULL,
	[max_serving] [nvarchar](2) NULL,
	[description_en] [nvarchar](255) NULL,
	[description_fr] [nvarchar](255) NULL,
	[category_id] [int] NOT NULL,
 CONSTRAINT [PK_Catering] PRIMARY KEY CLUSTERED 
(
	[catering_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[catering]  WITH CHECK ADD  CONSTRAINT [FK_Category] FOREIGN KEY([category_id])
REFERENCES [dbo].[WebCategories] ([CategId])
GO

ALTER TABLE [dbo].[catering] CHECK CONSTRAINT [FK_Category]
GO

ALTER TABLE [dbo].[catering]  WITH CHECK ADD  CONSTRAINT [FK_catering_detail_order_to_catering] FOREIGN KEY([category_id])
REFERENCES [dbo].[WebCategories] ([CategId])
GO

ALTER TABLE [dbo].[catering] CHECK CONSTRAINT [FK_catering_detail_order_to_catering]
GO


