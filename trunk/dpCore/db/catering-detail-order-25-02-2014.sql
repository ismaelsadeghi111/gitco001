
/****** Object:  Table [dbo].[catering_detail_order]    Script Date: 02/25/2014 10:08:46 Þ.Ù ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[catering_detail_order](
	[catering_detail_order_id] [bigint] IDENTITY(1,1) NOT NULL,
	[quantity] [int] NULL,
	[catering_order_id] [bigint] NOT NULL,
	[catering_id] [int] NOT NULL,
 CONSTRAINT [PK_catering_detail_order] PRIMARY KEY CLUSTERED 
(
	[catering_detail_order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[catering_detail_order]  WITH CHECK ADD  CONSTRAINT [FK_catering_detail_order_to_catering_order] FOREIGN KEY([catering_order_id])
REFERENCES [dbo].[catering_order] ([catering_order_id])
GO

ALTER TABLE [dbo].[catering_detail_order] CHECK CONSTRAINT [FK_catering_detail_order_to_catering_order]
GO


