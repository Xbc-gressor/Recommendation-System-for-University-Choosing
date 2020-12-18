# Recommendation System for University Choosing

A big data-based system for recommending universities for Chinese students.

## Architecture
[<img src="https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/Architecture.png"/>](https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/Architecture.png)

## Requirements

* Java Environment
* Tomcat(9.0.40)
* A patient heart

## To Do List
- [x] Upload System Architecture Image
- [ ] Upload Code
- [ ] Upload Demo GIF Images

## Functions
1. [Query the casting line of a university and the score lines of different majors](#function-1)
2. [Query the score line of one major in different universities](#function-2)
3. [Query the Admissions Regulations of different universities](#function-3)
4. [Recommend universities and majors](#function-4)

## Data
* [Google Drive](https://drive.google.com/file/d/1B73X0fv0peAv6m6VQZfnB7Tt9EnMiP9V/view?usp=sharing)

## Function 1
In this function, the user can query the casting line of a university and the score lines of different majors.
>The only thing the user need to do is to input  **the name of the university** in the search box and press the <kbd>Enter</kbd>.

[<img src="https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function1.gif"/>](https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function1.gif)

## Function 2
In this function, the user can query the score line of one major in different universities.
>The only thing the user need to do is to input **the name of the major** and choose the **province**, **year** and **student type(Science or Arts)**. After that, press the <kbd>Enter</kbd>.

[<img src="https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function2.gif"/>](https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function2.gif)

## Function 3
In this function, the user can query the Admissions Regulations of different universities.
>The only thing the user need to do is to input  **the name of the university** in the search box and press the <kbd>Enter</kbd>. (Just like the function 1)

[<img src="https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function3.gif"/>](https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function3.gif)

## Function 4
In this function, the system will recommend different major in different universities for the user.

>For a better recommendation, the user need to enter his  **score**, **province** and **student types(Science or Arts)**. After that, press the <kbd>Enter</kbd>.

[<img src="https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function4.gif"/>](https://github.com/Re-bin/Recommendation-System-for-University-Choosing/blob/main/functiongif/function4.gif)

## How To Use It
>Well, this is a good question, isn't it?

Before you start to install this project, I hope you are already familiar with the use of **tomcat** and **Eclipse**. Believe me, this project can be successfully configured according to the following process (because I did just that).

* Create a Dynamic Java web project in Eclipse, and select Tomcat v9.0 for Target runtime
* Import the jar package in the **lib** folder to the project (remember to Build Path)
* Unzip the **data** compression package and put it in a specific place (wherever you like)
* Change the dataPath variable in the Servlet Java files to the absolute address of your data folder
* Put the front-end code (in the **Interface** folder) into the **WebContent** folder of the web project
* Start the server and try to use it

If you encounter any problems during the process, please submit an issue.
