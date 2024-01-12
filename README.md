# SCHEDULING APPLICATION
**WGU - C195 - Software II - Advanced Java Concepts**

**Performance Assessment:**
QAM2 TASK 1: JAVA APPLICATION DEVELOPMENT  
Completed: 1/11/2024

Authored By: Cody Choules
Contact: codychls@gmail.com

---
## Description & Usage
**Purpose of the Application:**

This is a "learning project" (created primarily for educational or skill-building purposes) designed to cover a large array of skills using Java & OO Design (Object-Oriented). 

It is an appointment manager that works in conjunction with an existing database. The primary hypothetical purpose of the application is to give a user-friendly GUI (Graphical User Interface) to connect the users to the Data Base. The creation of this hypothetical application is for a contract with a global consulting organization that conducts business in multiple languages, countries, & first level divisions.

A future goal of mine for this project is to use it for a cyber-security war-game using and reorganizing it for an online database as an exploration in front-end exploitation.

---
## Dependencies, Installation, & Configuration

###- Versions Used / Tested -   
Rule of thumb is to utilize version closest to 17.0.9 if unclear
>- OS: Windows 10 x64

>- IDE: [IntelliJ Community Edition **2021.1.3**](https://www.jetbrains.com/idea/download/other.html) (Intergated Dev. Environment)

>- JDK: [**17.0.9**](https://www.oracle.com/java/technologies/downloads/#java17) (Java Dev. Kit)

>- JavaFX SDK: [**17.0.9**](https://gluonhq.com/products/javafx/) (Java 'Effects' Software Dev. Kit)

>- RAD: [Scene Builder **17.0.9**](https://gluonhq.com/products/scene-builder/) (Rapid Application Dev. Tool)

>- POM: [Apache Maven **4.0.0**](https://maven.apache.org/) *Auto implemented with IDE* (Project Object Model)

>- RDBMS: [MySQL **8.0.26**](https://dev.mysql.com/downloads/windows/installer/8.0.html) (Relational DataBase Management System)  
>>  -Selected Products during install:  
    --MySQL Server  
    --MySQL Workbench
  
>- JDBC: [MySQL/Connector/J **8.0.26**](https://dev.mysql.com/downloads/windows/installer/8.0.html) (Java DataBase Connector)


--- 
####Virtual Machine Options:

`--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics`

_Note: This is found in Edit configurations next to the build/run/debug options. It is under "Modify options">Add VM options

In Settings>Appearance & Behavior>**Path Variables** : add new variable: 
> >Name: `PATH_TO_FX`
> 
> assign the value to the javaFX lib
> 
> Example: 
> >`C:\Users\user\Downloads\C195-setup\openjfx-17.0.9_windows-x64_bin-sdk\javafx-sdk-17.0.9\lib`

---
**- Additional Information & Resources -**

dataBaseReadMe.md in this project has more info on setting up the DataBase

In addition, step-by-step project set-up, installation instructions, & notes are located in this Notion document [C195 Setup - Notion Sharable](https://www.notion.so/C195-Setup-Sharable-0181de44107f4bb7b188e353719dec2d?pvs=4)  
Note: this is not well organized but is in depth.
---

## Configuration

TODO List the ide, project, & vm option settings.

Configuration settings and environment variables are out of scope for this project but there are Dev tools "CChoulesDevTools" used to consolidate ease of use features for development. CChoulesDevTools.toolsOn/Off activates for the entire application. The most common use in printing relative information in the run terminal.

---

## Out Of Scope Features
During this project I included a number of features that were not included in the project. Below are those features and why they were included

>Dark mode 
> > Personal Goal to implement as white light is extremely irritating to my eyes & many programs do not have the feature.

> Language Switching
> > Helped with testing and was easy to implement.

> Search Function
> > Table
> > > Did not realize it was not required
> 
> > ComboBoxes
> > > When starting the code for the combo boxes I wanted to include an easy way to implement search amongst hundreds of items. Unfortunately, it turned out to be a bad idea as JFX ComboBox Skins has a number of unintuitive default settings and bugs at least in the way I wanted to interact with them.

---
## Additional Report

As required by rubric A3f I made the following report with the following tables.

> __Customer Totals Report__  
> This is meant to display information on customer distribution & acquisition.
> 
> > Customers By Country  
> This displays the total Customers per Country
> 
> > Customers By Division  
> This displays the total Customers per First level division.
>
> > Customers Gained By Month  
> This displays the total Customers created per month.

## Issues

The biggest issues are javaFX related. The combo box skin is still a little buggy despite disabling default listeners which had an even worse bug. The table element is not easily resizable and does not want to properly size itself to display contents in proportion. Both of these are aesthetic in effect and are fully functioning otherwise. 
