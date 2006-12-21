ALTER TABLE `ANNOUNCEMENT_BOARD` DROP COLUMN `READERS`;
ALTER TABLE `ANNOUNCEMENT_BOARD` CHANGE COLUMN `READERS_EXPRESSION` `READERS` TEXT;
ALTER TABLE `ANNOUNCEMENT_BOARD` DROP COLUMN `WRITERS`;
ALTER TABLE `ANNOUNCEMENT_BOARD` CHANGE COLUMN `WRITERS_EXPRESSION` `WRITERS` TEXT;
ALTER TABLE `ANNOUNCEMENT_BOARD` DROP COLUMN `MANAGERS`;
ALTER TABLE `ANNOUNCEMENT_BOARD` CHANGE COLUMN `MANAGERS_EXPRESSION` `MANAGERS` TEXT;
ALTER TABLE `PERSONAL_GROUP` DROP COLUMN `CONCRETE_GROUP`;
ALTER TABLE `PERSONAL_GROUP` CHANGE COLUMN `CONCRETE_GROUP_EXPRESSION` `CONCRETE_GROUP` TEXT;
ALTER TABLE `DEGREE_CURRICULAR_PLAN` DROP COLUMN `CURRICULAR_PLAN_MEMBERS_GROUP`;
ALTER TABLE `DEGREE_CURRICULAR_PLAN` CHANGE COLUMN `CURRICULAR_PLAN_MEMBERS_GROUP_EXPRESSION` `CURRICULAR_PLAN_MEMBERS_GROUP` TEXT;
ALTER TABLE `DEPARTMENT` DROP COLUMN `COMPETENCE_COURSE_MEMBERS_GROUP`;
ALTER TABLE `DEPARTMENT` CHANGE COLUMN `COMPETENCE_COURSE_MEMBERS_GROUP_EXPRESSION` `COMPETENCE_COURSE_MEMBERS_GROUP` TEXT;
ALTER TABLE `FILE` DROP COLUMN `PERMITTED_GROUP`;
ALTER TABLE `FILE` CHANGE COLUMN `PERMITTED_GROUP_EXPRESSION` `PERMITTED_GROUP` TEXT;
ALTER TABLE `SPACE` DROP COLUMN `PERSON_OCCUPATIONS_ACCESS_GROUP`;
ALTER TABLE `SPACE` CHANGE COLUMN `PERSON_OCCUPATIONS_ACCESS_GROUP_EXPRESSION` `PERSON_OCCUPATIONS_ACCESS_GROUP` TEXT;
ALTER TABLE `SPACE` DROP COLUMN `EXTENSION_OCCUPATIONS_ACCESS_GROUP`;
ALTER TABLE `SPACE` CHANGE COLUMN `EXTENSION_OCCUPATIONS_ACCESS_GROUP_EXPRESSION` `EXTENSION_OCCUPATIONS_ACCESS_GROUP` TEXT;
ALTER TABLE `SPACE` DROP COLUMN `SPACE_MANAGEMENT_ACCESS_GROUP`;
ALTER TABLE `SPACE` CHANGE COLUMN `SPACE_MANAGEMENT_ACCESS_GROUP_EXPRESSION` `SPACE_MANAGEMENT_ACCESS_GROUP` TEXT;
ALTER TABLE `STAFF_MANAGEMENT_SECTION` DROP COLUMN `SECTION_MANAGERS`;
ALTER TABLE `STAFF_MANAGEMENT_SECTION` CHANGE COLUMN `SECTION_MANAGERS_EXPRESSION` `SECTION_MANAGERS` TEXT;
ALTER TABLE `AVAILABILITY_POLICY` DROP COLUMN `TARGET_GROUP`;
ALTER TABLE `AVAILABILITY_POLICY` CHANGE COLUMN `TARGET_GROUP_EXPRESSION` `TARGET_GROUP` TEXT;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` DROP COLUMN `PHASES_MANAGEMENT_GROUP`;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` CHANGE COLUMN `PHASES_MANAGEMENT_GROUP_EXPRESSION` `PHASES_MANAGEMENT_GROUP` TEXT;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` DROP COLUMN `AUTOMATIC_VALUATION_GROUP`;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` CHANGE COLUMN `AUTOMATIC_VALUATION_GROUP_EXPRESSION` `AUTOMATIC_VALUATION_GROUP` TEXT;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` DROP COLUMN `OMISSION_CONFIGURATION_GROUP`;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` CHANGE COLUMN `OMISSION_CONFIGURATION_GROUP_EXPRESSION` `OMISSION_CONFIGURATION_GROUP` TEXT;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` DROP COLUMN `VALUATION_COMPETENCE_COURSES_AND_TEACHERS_MANAGEMENT_GROUP`;
ALTER TABLE `TEACHER_SERVICE_DISTRIBUTION` CHANGE COLUMN `VALUATION_COMPETENCE_COURSES_AND_TEACHERS_MANAGEMENT_GROUP_EXPR` `VALUATION_COMPETENCE_COURSES_AND_TEACHERS_MANAGEMENT_GROUP` TEXT;
ALTER TABLE `VALUATION_GROUPING` DROP COLUMN `COURSES_AND_TEACHERS_VALUATION_MANAGERS`;
ALTER TABLE `VALUATION_GROUPING` CHANGE COLUMN `COURSES_AND_TEACHERS_VALUATION_MANAGERS_EXPRESSION` `COURSES_AND_TEACHERS_VALUATION_MANAGERS` TEXT;
ALTER TABLE `VALUATION_GROUPING` DROP COLUMN `COURSES_AND_TEACHERS_MANAGEMENT_GROUP`;
ALTER TABLE `VALUATION_GROUPING` CHANGE COLUMN `COURSES_AND_TEACHERS_MANAGEMENT_GROUP_EXPRESSION` `COURSES_AND_TEACHERS_MANAGEMENT_GROUP` TEXT;
