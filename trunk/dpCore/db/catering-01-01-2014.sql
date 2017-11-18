USE [DblCentral]
GO

/****** Object:  Table [dbo].[Catering]    Script Date: 01/01/2014 12:48:42 �.� ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Catering](
	[CateringId] [int] IDENTITY(1,1) NOT NULL,
	[TitleEn] [nvarchar](50) NULL,
	[TitleFr] [nvarchar](50) NULL,
	[ImageUrl] [nvarchar](255) NULL,
	[MinServing] [nvarchar](2) NULL,
	[MaxServing] [nvarchar](2) NULL,
	[DescriptionEn] [nvarchar](255) NULL,
	[DescriptionFr] [nvarchar](255) NULL,
	[CategId] [int] NOT NULL,
 CONSTRAINT [PK_Catering] PRIMARY KEY CLUSTERED 
(
	[CateringId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Catering]  WITH CHECK ADD  CONSTRAINT [FK_Category] FOREIGN KEY([CategId])
REFERENCES [dbo].[WebCategories] ([CategId])
GO

ALTER TABLE [dbo].[Catering] CHECK CONSTRAINT [FK_Category]
GO

INSERT INTO
"WebCategories" ("CategId","DescEN","DescFR","TitleEN","TitleFR","Picture","CategSeq","CategStatus","URL_Field")
 VALUES
 (15,"Catering","Catering","Catering","Catering","catering_catering.jpg",15,1,"Catering");
