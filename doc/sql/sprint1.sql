ALTER TABLE `msyb_resource`.`squirrel_questions`
ADD COLUMN `lesson_id` int(11) DEFAULT 0 COMMENT 'level ID' AFTER `id`;
commit;

ALTER TABLE `msyb_resource`.`squirrel_lessons` 
ADD COLUMN `share_image` VARCHAR(128) NULL DEFAULT NULL COMMENT '分享页图片' AFTER `image`;
commit;

ALTER TABLE `msyb_resource`.`squirrel_levels` 
ADD COLUMN `introduction` VARCHAR(256) NULL DEFAULT NULL COMMENT '简介' AFTER `name`;
commit;

