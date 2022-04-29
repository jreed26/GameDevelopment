## Game Development Experience :A Summary.
### Jacob Reed
### The University Of West Florida
### Computer Science.
  


 - My experience with Game Development came in the form of an assignment during a course centered around Software Engineering. The assignment was a group-style project that was designed to take place incrementally over the course of the entire semester. The coursework breakdown was implemented by way of 3 separate sprints, lasting 2 weeks each. At the end of each sprint we had to complete a sprint retrospective. The retrospective evaluated 4 main categories: 
 
	-Deliverables Completed during the sprint.
	-A personal evaluation on the progress made (or didn’t make)
	-An evaluation of problems/ and or potential problems that may arise.
	-Items to complete during the next sprint.


- The project was designed to allow us to implement an Agile Framework, while also giving exposure to software engineering design principles such as:
	-MoSCoW
	-Gerkin Syntax 
	-Gestalt Design Principles
	-Software Design and Requirements
		-***and many others***


- The Application we developed was our choice, and by majority rule we decided on an adventure style videogame. In the group I focused mostly on Technical Design aspects, including elements such as:
	
	-Game Engine
	-Flow and Control Structure
	-State Management
	-Memory Management

- This project also presented ample exposure to Git Bash, and Version Control. I also took this opportunity to give myself to more exposure to the linux command line.

- With respect to the project specifically, and not the goals of the project, I was able to design, implement, and integrate several design patterns. 
I was also able to expose myself to Gradle and building applications. I was also able to implement the methodologies and strategies we were being taught 
in near real time. This application of concept so quickly is not an experience I have had often so it was enlightening to be able to actively apply new material.


***If Interested in viewing the source code/ Running the program, you will have to Set Up A Work Space. Below i will provide an installation and setup guide***.


# **Install Guide**

## SETUP
 - Download Eclipse: <a
   href="https://www.eclipse.org/downloads/">Here</a>
   <br> 
 - Download LibGdx: <a
   href="https://libgdx.com/wiki/start/setup#:~:text=(3.)-,Eclipse,Permalink,-JDK%208%2B%3A%20there">Here</a>
   <br> 
  - Download JDK 8+, to avoid distribution consistency errors,
   probably use Eclipse Adoptium 8, you can find it: <a
   href="https://adoptium.net/temurin/releases">Here</a> <br> 				 	 
	- Platform: Windows
		- Package: JDK
			- Version: 8

- Execute the LibGdx Installer, when its finished it should open a menu screen.
	 - You do not need to do anything in that screen, that is for creating new projects.




## Setting Up the Project Workspace

Now Open Eclipse and create a new Work Space:

<br>Go To:
- File:
	- **Import**: Existing Gradle Project

		- Next:
			- **Browse:** Find your project root folder and select it
				- Toggle "Override Workspace Settings"  

					- For: Gradle Distrubition: 
						- **make sure gradle wrapper is selected.** 
					- For: Advanced Options  
						- Gradle user home: 

							 1. Browse
							 2. Go to Project Repo
							 3. Go to .gradle file
							 4. select "6.7.1."

						- Java user Home

							 1. Browse
							 2. Go to Eclispse Adoptium 8 Installation Directory
							 3. Go to Eclipse Adoptium Distribution Folder
									 * (Should be in top layer)
							 4. select "8.0.3 - ... -hotspot"

			*At this point the set-up is finished, press Next to Build the imported project*

	## Build / Directory Stucture
 


In the Project Directory Structure there is a Game Directory, with 2 Sub directories:
- Game:
	- Game-core:
		- Source Code is located here.
	- Game-desktop:
		- To launch the game, you have to go into this directory and run the desktopLauncher class.

### Questions, Comments, Concerns: @jgr26@students.uwf.edu
