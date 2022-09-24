CREATE SCHEMA `spring-cafe`;

CREATE TABLE `spring-cafe`.`users`
(
    `id_user`           BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `login`             VARCHAR(45) NOT NULL,
    `password`          VARCHAR(45) NOT NULL,
    `name`              VARCHAR(45) NOT NULL,
    `surname`           VARCHAR(45) NOT NULL,
    `email`             VARCHAR(45) NOT NULL,
    `phone_number`      VARCHAR(45) NOT NULL,
    `birthday`          DATE        NOT NULL,
    `registration_time` DATETIME    NOT NULL,
    `photo_path`        VARCHAR(45) NULL,
    `id_role`           BIGINT      NOT NULL,
    UNIQUE INDEX `id_user_UNIQUE` (`id_user` ASC) VISIBLE,
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    CONSTRAINT `userRole`
        FOREIGN KEY (`id_role`)
            REFERENCES `spring-cafe`.`roles` (`id_role`)
            ON DELETE CASCADE
            ON UPDATE CASCADE

) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`clients`
(
    `id_client`      BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `block`          TINYINT NOT NULL DEFAULT 0,
    `loyalty_points` DOUBLE  NOT NULL DEFAULT 0,
    `client_account` DOUBLE  NOT NULL DEFAULT 0,
    `id_user`        BIGINT  NOT NULL,
    UNIQUE INDEX `id_client_UNIQUE` (`id_client` ASC) VISIBLE,
    CONSTRAINT `userClient`
        FOREIGN KEY (`id_user`)
            REFERENCES `spring-cafe`.`users` (`id_user`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`administrators`
(
    `id_administrator` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `experience`       DOUBLE NOT NULL DEFAULT 0,
    `id_status`        BIGINT NOT NULL DEFAULT 0,
    `id_user`          BIGINT NOT NULL,
    UNIQUE INDEX `id_administrator_UNIQUE` (`id_administrator` ASC) VISIBLE,
    CONSTRAINT `adminUser`
        FOREIGN KEY (`id_user`)
            REFERENCES `spring-cafe`.`users` (`id_user`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `adminStatus`
        FOREIGN KEY (`id_status`)
            REFERENCES `spring-cafe`.`statuses` (`id_status`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`ingredients`
(
    `id_ingredient` BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`          VARCHAR(45) NOT NULL,
    `shelf_life`    DATE        NOT NULL,
    `grams`         DOUBLE      NOT NULL DEFAULT 0,
    UNIQUE INDEX `id_ingredient_UNIQUE` (`id_ingredient` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`products`
(
    `id_product`    BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`          VARCHAR(45) NOT NULL,
    `validity_date` DATETIME    NOT NULL,
    `registration_time` DATETIME    NOT NULL,
    `price`         DOUBLE      NOT NULL DEFAULT 0,
    `photo_path`    VARCHAR(45) NULL,
    PRIMARY KEY (`id_product`),
    UNIQUE INDEX `idproduct_UNIQUE` (`id_product` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`orders`
(
    `id_order`          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`              VARCHAR(45) NOT NULL,
    `date_of_receiving` DATETIME    NOT NULL,
    `price`             DOUBLE      NOT NULL DEFAULT 0,
    `received`          TINYINT     NOT NULL DEFAULT 0,
    `id_payment_type`   BIGINT      NOT NULL,
    `id_client`         BIGINT      NOT NULL,
    UNIQUE INDEX `id_order_UNIQUE` (`id_order` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    CONSTRAINT `orderClient`
        FOREIGN KEY (`id_client`)
            REFERENCES `spring-cafe`.`clients` (`id_client`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `orderPaymentType`
        FOREIGN KEY (`id_payment_type`)
            REFERENCES `spring-cafe`.`payment_types` (`id_payment_type`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`payment_types`
(
    `id_payment_type` BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`            VARCHAR(45) NOT NULL,
    UNIQUE INDEX `id_payment_type_UNIQUE` (`id_payment_type` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`roles`
(
    `id_role` BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`    VARCHAR(45) NOT NULL,
    UNIQUE INDEX `id_role_UNIQUE` (`id_role` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`statuses`
(
    `id_status` BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`      VARCHAR(45) NOT NULL,
    UNIQUE INDEX `idstatuses_UNIQUE` (`id_status` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`orders_products`
(
    `id_order`   BIGINT NOT NULL PRIMARY KEY,
    `id_product` BIGINT NOT NULL PRIMARY KEY,
    INDEX `order_idx` (`id_order` ASC) VISIBLE,
    INDEX `product_idx` (`id_product` ASC) VISIBLE,
    CONSTRAINT `order`
        FOREIGN KEY (`id_order`)
            REFERENCES `spring-cafe`.`orders` (`id_order`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `product`
        FOREIGN KEY (`id_product`)
            REFERENCES `spring-cafe`.`products` (`id_product`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `spring-cafe`.`products_ingredients`
(
    `id_product`    BIGINT NOT NULL PRIMARY KEY,
    `id_ingredient` BIGINT NOT NULL PRIMARY KEY,
    INDEX `product_idx` (`id_product` ASC) VISIBLE,
    INDEX `ingredient_idx` (`id_ingredient` ASC) VISIBLE,
    CONSTRAINT `product2`
        FOREIGN KEY (`id_product`)
            REFERENCES `spring-cafe`.`products` (`id_product`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `ingredient`
        FOREIGN KEY (`id_ingredient`)
            REFERENCES `spring-cafe`.`ingredients` (`id_ingredient`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE = InnoDB;

INSERT INTO `spring-cafe`.`roles` (`name`)
VALUES ('CLIENT');
INSERT INTO `spring-cafe`.`roles` (`name`)
VALUES ('ADMINISTRATOR');

INSERT INTO `spring-cafe`.`statuses` (`name`)
VALUES ('ACCEPTED');
INSERT INTO `spring-cafe`.`statuses` (`name`)
VALUES ('WAITING');
INSERT INTO `spring-cafe`.`statuses` (`name`)
VALUES ('DECLINED');

INSERT INTO `spring-cafe`.`payment_types` (`name`)
VALUES ('CASH');
INSERT INTO `spring-cafe`.`payment_types` (`name`)
VALUES ('CLIENT_ACCOUNT');


