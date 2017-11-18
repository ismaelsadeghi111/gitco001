USE [DblCentral]
GO

/****** Object:  Table [dbo].[dpdollardays]    Script Date: 12/18/2013 1:49:19 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[dpdollardays](
	[dpdollardays_id] [bigint] IDENTITY(1,1) NOT NULL,
	[sunday_percentage] [float] NULL,
	[monday_percentage] [float] NULL,
	[tuesday_percentage] [float] NULL,
	[wednesday_percentage] [float] NULL,
	[thursday_percentage] [float] NULL,
	[friday_percentage] [float] NULL,
	[saturday_percentage] [float] NULL,
	[creation_date] [datetime] DEFAULT GETDATE(),
	[status] [smallint] NULL,
PRIMARY KEY CLUSTERED
(
	[dpdollardays_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


