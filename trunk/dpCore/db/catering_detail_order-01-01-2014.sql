USE [DblCentral]
GO

/****** Object:  Table [dbo].[CateringDetail]    Script Date: 01/01/2014 12:55:55 È.Ù ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CateringDetail](
	[CateringDetailId] [int] IDENTITY(1,1) NOT NULL,
	[CateringOrderId] [int] NOT NULL,
	[CateringId] [int] NOT NULL,
 CONSTRAINT [PK_CateringDetail] PRIMARY KEY CLUSTERED 
(
	[CateringDetailId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[CateringDetail]  WITH CHECK ADD  CONSTRAINT [FK_CateringDetail_Catering] FOREIGN KEY([CateringId])
REFERENCES [dbo].[Catering] ([CateringId])
GO

ALTER TABLE [dbo].[CateringDetail] CHECK CONSTRAINT [FK_CateringDetail_Catering]
GO


