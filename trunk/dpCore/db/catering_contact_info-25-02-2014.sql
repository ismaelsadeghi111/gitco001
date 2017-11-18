

/****** Object:  Table [dbo].[catering_contact_info]    Script Date: 02/25/2014 10:07:57 Þ.Ù ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[catering_contact_info](
	[catering_contact_info_id] [int] IDENTITY(1,1) NOT NULL,
	[f_name] [nvarchar](50) NULL,
	[l_name] [nvarchar](50) NULL,
	[address] [nvarchar](255) NULL,
	[phone] [nvarchar](10) NULL,
	[delivery_date] [nvarchar](20) NULL,
	[ext] [nvarchar](10) NULL,
	[gender] [bit] NULL,
 CONSTRAINT [PK_Catering_Contact_Info] PRIMARY KEY CLUSTERED 
(
	[catering_contact_info_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


