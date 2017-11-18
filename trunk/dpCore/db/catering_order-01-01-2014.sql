USE [DblCentral]
GO

/****** Object:  Table [dbo].[CateringOrder]    Script Date: 01/01/2014 12:50:19 È.Ù ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CateringOrder](
	[CateringOrderId] [int]  IDENTITY(1,1) NOT NULL,
	[Quantity] [int] NULL,
	[OrderDate] [datetime] NULL,
	[CustomerNote] [nvarchar](255) NULL,
	[CateringContactInfoId] [int] NOT NULL,
 CONSTRAINT [PK_CateringOrder] PRIMARY KEY CLUSTERED 
(
	[CateringOrderId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[CateringOrder]  WITH CHECK ADD  CONSTRAINT [FK_CateringOrder_Catering_Contact_Info] FOREIGN KEY([CateringContactInfoId])
REFERENCES [dbo].[Catering_Contact_Info] ([CateringContactInfoId])
GO

ALTER TABLE [dbo].[CateringOrder] CHECK CONSTRAINT [FK_CateringOrder_Catering_Contact_Info]
GO


