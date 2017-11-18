USE [DblCentral]
GO

/****** Object:  Table [dbo].[dpdollarschadule]    Script Date: 18/02/2014 7:47:36 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[dpdollarschadule](
	[dpdollarschadule_Id] [bigint] IDENTITY(1,1) NOT NULL,
	[end_dpdollar_date] [datetime] NULL,
	[percentage] [float] NULL,
	[start_dpdollar_date] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[dpdollarschadule_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[dpdollarschadule] ADD  DEFAULT (getdate()) FOR [end_dpdollar_date]
GO


