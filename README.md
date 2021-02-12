# Poseidon

## Table of content

* [Technical Informations](#1-technical-informations)
    * [Requirements](#11-requirements)
    * [Setup IntelliJ](#12-setup-with-intellij-ide)
    * [Credentials](#13-credentials)
* [Security](#2-security)
    * [Important Note](#21-important-note)

## 1. Technical informations:

---

### 1.1 Requirements

![Java Version](https://img.shields.io/badge/Java-8.0-red)
![Maven Version](https://img.shields.io/badge/Maven-3.6.3-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0.21-cyan)
![SpringBoot Version](https://img.shields.io/badge/Spring%20Boot-2.4.2-brightgreen)
![SpringSecurity Version](https://img.shields.io/badge/Spring%20Security-5.3.3-brightgreen)
![J-Unit Version](https://img.shields.io/badge/JUnit-5.7.0-orange)
![ThymeLeaf Version](https://img.shields.io/badge/ThymeLeaf-3.0.12-orange)
![ThymeLeaf Extras Version](https://img.shields.io/badge/ThymeLeaf%20Extras%20Java%208%20Runtime-3.0.4-orange)
![TomCat](https://img.shields.io/badge/TomCat:8080-9.0.41-brightgreen)
![BootStrap Version](https://img.shields.io/badge/BootStrap-4.3.1-blue)
![Coverage](https://img.shields.io/badge/Coverage%20with%20IT-95%25-green)

### 1.2 Setup with Intellij IDE

1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create a database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

### 1.3 Credentials

User already in database

```bash
login: admin  password: Admin123@
login: user  password: User123@
```

Database credentials are encoded with Jasypt. For more information on Jasypt and its usage visit:
http://www.jasypt.org/

Online Tools to generate Jasypt string:
https://www.devglan.com/online-tools/jasypt-online-encryption-decryption

### 1.4 Export project

Export project with:

```bash
mvn clean
mvn install
```

As Tomcat is embbeded, you can launch directly the *.jar with

```bash
java -jar {nameOfTheFile}.jar
```

## 2 Security

---
Spring Security is use for login and access control to uris. You can configure it by editing the
WebSecurityConfig file. Two defaults role are setted by the already created users in database :
ADMIN, USER.

If you want to create other roles, you can directly write the role in database, of use the /user/add
as admin with the UI. If you do so, don't forget to edit the template to make the role choice
available on the UI

## 2.1 ***IMPORTANT NOTE:***

The string allowing the decoding of the credentials written in application. properties, is hardcoded
to simplify the development.

***DO NOT USE AS IT IS FOR PRODUCTION !***\
If you uncomment the line in application.properties to enable launch with environnement variable to
launch the .jar with the password for jsypt so type

```bash
JASYPT_ENCRYPTOR_PASSWORD=jesecureunedbcommejepeut -jar {nameOfTheFile}.jar
```

