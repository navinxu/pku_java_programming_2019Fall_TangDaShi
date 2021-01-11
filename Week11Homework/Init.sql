--
-- File generated with SQLiteStudio v3.2.1 on Thu Dec 5 16:13:57 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: people
DROP TABLE IF EXISTS people;

CREATE TABLE people (
    id          INTEGER       PRIMARY KEY AUTOINCREMENT
                              NOT NULL,
    name        VARCHAR (512) NOT NULL,
    birthday    VARCHAR (512),
    phonenumber VARCHAR (512),
    wechat      VARCHAR (512),
    qq          VARCHAR (512),
    email       VARCHAR (512),
    category    VARCHAR (512),
    others      VARCHAR (512) 
);

INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, 'XiaoMingLiao', '19991122', '982736785', '786823545', '89761255', 'mliao@122.com', '同学', '留学生');
INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '金毛狮王', '11150101', '9837493274', 'kjajkjjj', '293847928374', 'dasodfn@lsjaf.com', '大侠', '');
INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '张无忌', '10001202', '13800138123', 'zwjok2', '982374876', 'zwjok@1256.com', '武林高手', '');
INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '柳剑生', '19920111', '123987346687', 'Xsjddkjhf12334', '23894792374', 'ljs1992@1255.com', '朋友', '金融高手');
INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '徐云', '19900122', '132987397498', 'xuyun128', '928374932874', 'xuyun@hotemail.com', '朋友', '兵王');
INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '王大胆', '19990219', '13518972347', '1990wdd', '2389479837', 'wdd1990@1266.com', '朋友', '作战人员');
INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '黎南', '19901202', '138000138000', 'linan168', '89237437897', 'aaadkj@kk.com', '朋友', '隐世家族子弟');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
