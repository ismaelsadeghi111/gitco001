
/****** Object:  Table [dbo].[catering_order]    Script Date: 02/25/2014 10:09:28 Þ.Ù ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[catering_order](
	[catering_order_id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_date] [nvarchar](50) NULL,
	[customer_note] [nvarchar](255) NULL,
	[catering_contact_info_id] [int] NOT NULL,
	[sub_total_price] [float] NULL,
	[total_price] [float] NULL,
 CONSTRAINT [PK_catering_order] PRIMARY KEY CLUSTERED 
(
	[catering_order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[catering_order] ADD  DEFAULT ('00.00') FOR [sub_total_price]
GO

ALTER TABLE [dbo].[catering_order]  WITH CHECK ADD  CONSTRAINT [FK_catering_contact_info] FOREIGN KEY([catering_contact_info_id])
REFERENCES [dbo].[catering_contact_info] ([catering_contact_info_id])
GO

ALTER TABLE [dbo].[catering_order] CHECK CONSTRAINT [FK_catering_contact_info]
GO


