DROP DATABASE [IF EXISTS] chatroom; --The IF EXISTS option is available from SQL Server 2016 (13.x).

CREATE DATABASE chatroom;

USE [chatroom]
GO
/****** Object:  Table [dbo].[rooms]    Script Date: 10/18/2021 10:40:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rooms](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ROOM_NAME] [varchar](225) NULL,
	[DELETED] [bit] NULL,
 CONSTRAINT [prm_key_id] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[rooms] ON 

INSERT [dbo].[rooms] ([ID], [ROOM_NAME], [DELETED]) VALUES (1, N'room.1', 0)
INSERT [dbo].[rooms] ([ID], [ROOM_NAME], [DELETED]) VALUES (2, N'room.2', 0)
SET IDENTITY_INSERT [dbo].[rooms] OFF
ALTER TABLE [dbo].[rooms] ADD  DEFAULT ('') FOR [ROOM_NAME]
GO
ALTER TABLE [dbo].[rooms] ADD  DEFAULT ((0)) FOR [DELETED]
GO
