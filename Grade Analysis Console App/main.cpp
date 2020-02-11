/*main.cpp*/

//
// Kent Paglomotan
// U. of Illinois, Chicago
// CS 341, Fall 2019
// Project #03: GradeUtil UI
// This program is C++11 dependent
//

#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <algorithm>
using namespace std;

// includes for gradeutil
#include "gradeutil.h"

College InputGradeData(string filename)
{
    College college;
    ifstream file(filename);
    string line, value;

    if (!file.good())
    {
        cout << "**Error: unable to open input file '" << filename << "'." << endl;
        return college;
    }

    // first line contains semester,year
    getline(file, line);
    stringstream ss(line);

    getline(ss, college.Name, ',');
    getline(ss, college.Semester, ',');
    getline(ss, value);
    college.Year = stoi(value);

    // second line contains column headers --- skip
    getline(file, line);

    //
    // now start inputting and parse course data:
    //

    while (getline(file, line))
    {
        Course c = ParseCourse(line);

        //
        // search for correct dept to ask course to, otherwise create a new dept:
        //
        //
        auto dept_iter = std::find_if(college.Depts.begin(),
                                      college.Depts.end(),
                                      [&](const Dept &d) {
                                          return (d.Name == c.Dept);
                                      });

        if (dept_iter == college.Depts.end())
        {
            //
            // doesn't exist, so we have to create a new dept
            // and insert course:
            //
            Dept d(c.Dept);

            d.Courses.push_back(c);

            college.Depts.push_back(d);
        }
        else
        {
            // dept exists, so insert course into existing dept:
            dept_iter->Courses.push_back(c);
        }

    } //while

    //
    // done:
    //
    return college;
}

// TODO: define your own functions
void printCollegeSummary(College college, int DFW, int N)
{ // Displays the College summary
  cout << "** College of " << college.Name << ", " << college.Semester << " " << college.Year << " **" << endl;
  cout << "# of courses taught: " << college.NumCourses()<< endl;
  cout << "# of students taught: " << college.NumStudents() << endl;
  cout << "grade distribution (A-F): " 
       << GetGradeDistribution(college).PercentA << "%, " 
       << GetGradeDistribution(college).PercentB << "%, " 
       << GetGradeDistribution(college).PercentC << "%, " 
       << GetGradeDistribution(college).PercentD << "%, " 
       << GetGradeDistribution(college).PercentF << "% " 
       << endl;
  cout << "DFW rate: " << GetDFWRate(college, DFW, N) << "%" << endl;
  cout << endl;
}

void printDeptSummary(Dept dept, int DFW, int N)
{ // Displays the Department Summary
  GradeStats stats = GetGradeDistribution(dept);
  cout << dept.Name << ":" << endl;
  cout << " # courses taught: " << dept.NumCourses()<< endl;
  cout << " # students taught: " << dept.NumStudents() << endl;
  cout << " grade distribution (A-F): "
       << stats.PercentA << "%, "
       << stats.PercentB << "%, "
       << stats.PercentC << "%, "
       << stats.PercentD << "%, "
       << stats.PercentF << "% " << endl;
       cout << " DFW rate: " << GetDFWRate(dept, DFW, N) << "%" << endl;
}

string compareGradingType(Course course)
{ // Compares the course grading type and returns a string
  string grade;
  
  if (course.getGradingType() == Course::Letter){
    return grade = "letter";
  }
  
  if (course.getGradingType() == Course::Satisfactory){
    return grade = "satisfactory";
  }
    
  else {
    return grade = "unknown";
  }
}

void printCommonStats(Course c, int DFW, int N)
{ // Displays the most common format for different commands
    GradeStats stats = GetGradeDistribution(c);
    cout << c.Dept << " " << c.Number << " (section " << c.Section << "): " << c.Instructor << endl;
    cout << " # students: " << c.getNumStudents() << endl;
    cout << " course type: " << compareGradingType(c) << endl;
    cout << " grade distribution (A-F): " << stats.PercentA << "%, " << stats.PercentB << "%, "
         << stats.PercentC << "%, " << stats.PercentD << "%, " << stats.PercentF << "% " << endl;
    cout << " DFW rate: " << GetDFWRate(c, DFW, N) << "%" << endl;
}

bool checkDept(College college, string deptName)
{ // A boolean that checks the department is there or not
  if(deptName == "all")
  { // If the input is all for department then it goes back to the command
    return true;
  }
  // Finds the department name 
  auto findDeptName = std::find_if (college.Depts.begin(), college.Depts.end(), 
                                            [=](const Dept &d)
                                            {
                                              if (d.Name == deptName){
                                                return true;
                                              }
                                              else {
                                                return false;
                                              }
                                            }

                                           );

   if (findDeptName == college.Depts.end())
   { // Checks if the department name is in the college
     return false;
   }
  
   else
   { // If it is the returns true
     return true;
   }
   
}

vector<Course> SatisfactoryDept(const Dept& dept)
{ // This function find the department that have a satisfactory grading type
  vector<Course> courses;
  for(const Course& course : dept.Courses)
  { // loops through the courses
    if (course.getGradingType() == Course::Satisfactory)
    { // match: Satisfactory
      courses.push_back(course); // adds the matching course to the vector
    }
  }
  
  sort(courses.begin(), courses.end(),  // If the courses have the same department name sort by section
      [](const Course& c1, const Course& c2)
      {
		return (c1.Section < c2.Section);
      }
    );
  
  return courses;
}

vector<Course> SatisfactoryCollege(const College& college)
{ // This function find the college that have a satisfactory grading type
  vector<Course>  courses;
  for(const Dept& dept : college.Depts) // nested for loop that goes through college, department, college
  { 
    vector<Course> onedept = SatisfactoryDept(dept);
    for(const Course& c : onedept)
    {
      courses.push_back(c); // adds the matching course to the vector
    }
  }
  
  sort(courses.begin(), courses.end(),  // sort by department, course number, section
      [](const Course& c1, const Course& c2)
      {
        if (c1.Dept < c2.Dept)
          return true;
        else if (c1.Dept > c2.Dept)
          return false;
        else // same dept, look at course #:
          if (c1.Number < c2.Number)
            return true;
          else if (c1.Number > c2.Number)
            return false;
          else // same course #, look at section #:
            if (c1.Section < c2.Section)
              return true;
            else 
              return false;
      }
    );
  
  return courses;
}

void printSatisfactory(Course c)
{ //Displays the satisfactory stats
 cout << c.Dept << " " << c.Number << " (section " << c.Section << "): " << c.Instructor << endl;
 cout << " # students: " << c.getNumStudents() << endl;
 cout << " course type: " << compareGradingType(c) << endl;
}

double getPercentB(Course c)
{ // Returns only the letter B percent
  GradeStats statsB = GetGradeDistribution(c);
  
  return statsB.PercentB;
}

double getCourseGPA(const Course& c)
{ // calculate the GPA for course
  
  double GPA = (c.NumA * 4.00) + (c.NumB * 3.00) + (c.NumC * 2.00) + (c.NumD * 1.00) + (c.NumF * 0.00);
  int score = c.NumA + c.NumB + c.NumC + c.NumD + c.NumF;
  if (score == 0)
  {
    return 0.0;
  }
  return (GPA / score);
}

double getDeptGPA(const Dept& d)
{ //calculate the Department GPA
  double totalGPA = 0.0;
  int courseSize = 0;
  
  for(const Course& c : d.Courses)
  {
    if ((c.NumA + c.NumB + c.NumC + c.NumD + c.NumF) > 0){ // checks the GradingType Letter
      totalGPA += getCourseGPA(c);
      courseSize++; // increment the course size
    }
  }
  
  double GPA = ((double)totalGPA / (double)courseSize); // calculate the GPA
  
  if (courseSize == 0){
    return 0.0; // checks the size just in case
  }
  
  else {
    return GPA;
  }
}

int main()
{
    string filename;
  
    string command;
    string deptName;
    int DFW = 0;
    int N = 0;
    
  
    cout << std::fixed;
    cout << std::setprecision(2);

    //
    // 1. Input the filename and then the grade data:
    //
    cin >> filename;
    //filename = "fall-2018.csv";

    College college = InputGradeData(filename);
    
    // 2. TODO: print out summary of the college
    // DEFINE your own functions
    printCollegeSummary(college, DFW, N);
  
    // 3. TODO: Start executing commands from the user:
    // DEFINE your own functions
    while (true){
      cout << "Enter a command> ";
      cin >> command;

      if (command == "#")
      {
        break;
      }
      
      else if (command == "summary")
      {
        cout << "dept name, or all? ";
        cin >> deptName;

        if (deptName == "all")
        {
          sort(college.Depts.begin(), college.Depts.end(), 
                 [](const Dept& d1, const Dept& d2)
                 {
                   if (d1.Name < d2.Name){
                     return true;
                   }
                   else {
                     return false;
                   }
                 }
               );
          
          for (Dept dept: college.Depts){
            printDeptSummary(dept, DFW, N);
          } // end of the range based for loop
        } // end of deptName all
        
        else {
          auto findDeptName = std::find_if (college.Depts.begin(), college.Depts.end(), 
                                            [=](const Dept &d)
                                            {
                                              if (d.Name == deptName){
                                                return true;
                                              }
                                              else {
                                                return false;
                                              }
                                            }

                                           );

          if (findDeptName == college.Depts.end())
          {
            cout << "**dept not found" << endl;
          }
          
          else 
          {
            Dept dept(*findDeptName);
            printDeptSummary(dept, DFW, N); 
          }
        } // end of the else statement
      } // end of the summary command

      else if (command == "search")
      {
        cout << "dept name, or all? ";
        cin >> deptName;
     
        bool deptExist= checkDept(college, deptName);
        string instructorPrefix;
        int courseNum;
        
        cout << "course # or instructor prefix? ";
        cin >> instructorPrefix;
        
        if (deptExist == true) {
          int i = 0; 
          
          stringstream ss(instructorPrefix);
          ss >> courseNum;
  
          if (ss.fail()){
            vector<Course> CoursesPre = FindCourses(college, instructorPrefix);
            for (Course c: CoursesPre){
              if (deptName == "all")
              {
                i++; // keeps track of the valid input
                printCommonStats(c, DFW, N);
              }
              
              else {
                if (deptName != c.Dept)
                { // Doesn't belong to department
                  continue;
                } // end of if statement
                
                else{
                  i++; // keeps track of the valid input
                  printCommonStats(c, DFW, N);
                } // end of the second else statement
              } // end of the first else statement
            } // end of range based for loop
            
            if (i == 0)
            { // checks for invalid inputs
              cout << "**none found" << endl;
            } // end of if none found
          } // end of ss.fail

          else 
          {
            vector<Course> CoursesNum = FindCourses(college, courseNum);
            for (Course c: CoursesNum)
            {
              if (deptName == "all")
              {
                i++;
                printCommonStats(c, DFW, N);
              } // end of if dept all
              
              else 
              {
                if (deptName != c.Dept)
                { // Doesn't belong to department
                  continue;
                }
                
                else
                {
                  i++;
                  printCommonStats(c, DFW, N);
                } // end of the inner1 else statement
              } // end of the inner2else statement
            } // end of the ranged based for loop
            
            if (i == 0)
            { // checks for invalid inputs
              cout << "**none found" << endl;
            } // end of if none found
          }// end of else of ss.fail
        } // end of if deptExist
        
        else 
        {
          cout << "**dept not found" << endl;
        }
      } // end of the search command
      
      else if (command == "satisfactory")
      { 
        cout << "dept name, or all? ";
        cin >> deptName;
        bool deptExist= checkDept(college, deptName);
        
        if (deptExist == true) 
        {
            int i = 0;
            vector<Course> Courses = SatisfactoryCollege(college);
          
            if (deptName == "all")
            {
              for (Course c: Courses)
              {
                i++;
                printSatisfactory(c);
              } // end of range for loop
            } // end of if statement
          
            else 
            {
              for (Course c: Courses){
                if (deptName != c.Dept)
                { // Doesn't belong to department
                    continue;
                } 
                
                else
                {
                  i++;
                  printSatisfactory(c);
                } // end of inner else
              } // end of range for loop
              
              if (i == 0)
              {
                cout << "**none found" << endl;
              } // end of if none found
            } // end of outer else
          } // end of deptExist
        
          else 
          {
            cout << "**dept not found" << endl;
          }
        } // end of the satisfactory command
      
      else if (command == "dfw")
      {
        cout << "dept name, or all? ";
        cin >> deptName;
        
        bool deptExist= checkDept(college, deptName);
        double dfwThresh;
        
        cout << "dfw threshold? ";
        cin >> dfwThresh;
        
        if (deptExist == true) 
        {
          int i = 0;
          vector<Course> deptDFW = FindCourses(college, "");

          sort(deptDFW.begin(), deptDFW.end(), 
                   [](const Course& c1, const Course& c2)
                   {
                     int DFW1 = 0;
                     int DFW2 = 0;
                     int N1 = 0;
                     int N2 = 0;
                     double rate1 = GetDFWRate(c1, DFW1, N1);
                     double rate2 = GetDFWRate(c2, DFW2, N2);
                     if (rate1 > rate2)
                       return true;
                     else if (rate1 < rate2)
                       return false;
                     else 
                       if (c1.Dept < c2.Dept)
                         return true;
                       else if (c1.Dept > c2.Dept)
                         return false;
                       else // same dept, look at course #:
                        if (c1.Number < c2.Number)
                          return true;
                        else if (c1.Number > c2.Number)
                          return false;
                        else // same course #, look at section #:
                          if (c1.Section < c2.Section)
                            return true;
                          else 
                            return false;
                   } // end of the sort
                 );

          if (deptName == "all")
          {
              for (Course c: deptDFW)
              {
                if (GetDFWRate(c, DFW, N) > dfwThresh)
                {
                  i++;
                  printCommonStats(c, DFW, N);
                } // end of if dfw > dfwThresh
              } // end of the range based for loop
            
              if (i == 0)
              {
                cout << "**none found" << endl;
              } // end of if none found
          } // end of if deptName all
          
          else 
          {
            for (Course c: deptDFW){
                  if (deptName != c.Dept)
                  { // Doesn't belong to department
                      continue;
                  } 
              
                  else
                  {
                    if (GetDFWRate(c, DFW, N) > dfwThresh)
                    {
                      i++;
                      printCommonStats(c, DFW, N);
                    } // end of if dfw > dfwThresh
                  }// end of the inner else
            } // end of the range based for loop
            
            if (i == 0)
              {
                cout << "**none found" << endl;
              } // end of if none found
          }// end of outer else
        } // end of deptExist
        
        else 
        {
          cout << "**dept not found" << endl;
        } // end of dep not found
      } // end of the dfw command
      
      else if (command == "letterB")
      {
        cout << "dept name, or all? ";
        cin >> deptName;
        bool deptExist= checkDept(college, deptName);
        
        double letterBThresh; // int or double?
        cout << "letter B threshold? ";
        cin >> letterBThresh;
        
        if(deptExist == true)
        {
          int i = 0;
          vector<Course> courseLetterB = FindCourses(college, "");
          
          sort(courseLetterB.begin(), courseLetterB.end(), 
                   [](const Course& c1, const Course& c2)
                   {
                     if (getPercentB(c1) > getPercentB(c2))
                     {
                       return true;
                     }
                     else 
                     {
                       return false;
                     }
                   }
                 );
          
          if (deptName == "all")
          {
              for (Course c: courseLetterB)
              {
                if (getPercentB(c) > letterBThresh)
                {
                  i++;
                  printCommonStats(c, DFW, N);
                } 
              } // end of the range based for loop
              if (i == 0)
                {
                  cout << "**none found" << endl;
                } // end of if none found
          } // end of if deptName all
          
          else 
          {
            for (Course c: courseLetterB){
                  if (deptName != c.Dept)
                  { // Doesn't belong to department
                      continue;
                  } 
              
                  else
                  {
                    if (getPercentB(c) > letterBThresh)
                    {
                      i++;
                      printCommonStats(c, DFW, N);
                    } 
                  }// end of the inner else
            } // end of the range based for loop
            
            if (i == 0)
              {
                cout << "**none found" << endl;
              } // end of if none found
          }// end of outer else
        } // end of deptExist
        
        else 
        {
          cout << "**dept not found" << endl;
        } // end of dept not found
      } // end of the letterB command
      
      else if (command == "average")
      {
        cout << "dept name, or all? ";
        cin >> deptName;
        bool deptExist= checkDept(college, deptName);
        
        if (deptExist == true)
        {
          if (deptName == "all")
          {
            sort(college.Depts.begin(), college.Depts.end(), 
                   [](const Dept& d1, const Dept& d2)
                 {
                   if (getDeptGPA(d1) > getDeptGPA(d2))
                       return true;
                     else if (getDeptGPA(d1) < getDeptGPA(d2))
                       return false;
                     else 
                       if (d1.Name < d2.Name)
                         return true;
                       else if (d1.Name > d2.Name)
                         return false;
                 }
            );
            
            for (Dept dept: college.Depts)
            {
              cout << "Overall GPA for "<< dept.Name << " : " << getDeptGPA(dept) << endl;
            } // eng of the range based for loop
          } // end of dept name all
          
          else 
          {
            // sad boi cant calculate the GPA right
            vector<Course> courseAvg = FindCourses(college, "");
            sort(courseAvg.begin(), courseAvg.end(), 
                   [](const Course& c1, const Course& c2)
                 {
                   if (getCourseGPA(c1) > getCourseGPA(c2))
                    return true;
                     else if (getCourseGPA(c1) < getCourseGPA(c2))
                       return false;
                     else 
                       if (c1.Dept < c2.Dept)
                         return true;
                       else if (c1.Dept > c2.Dept)
                         return false;
                       else // same dept, look at course #:
                        if (c1.Number < c2.Number)
                          return true;
                        else if (c1.Number > c2.Number)
                          return false;
                        else // same course #, look at section #:
                          if (c1.Section < c2.Section)
                            return true;
                          else 
                            return false;
                 }
                );
            
              for (Course c: courseAvg)
              {
                if(deptName != c.Dept)
                {
                  continue;
                }
                else if (getCourseGPA(c) == 0){
                  continue;
                }
                else 
                {
                  cout << "Overall GPA for "<< c.Dept << " " << c.Number << "(" << c.Section << ")" << " : " << getCourseGPA(c) << endl;
                }
              } // end of range based loop
          } // end of the outer else
        }
        
        else 
        {
          cout << "**dept not found" << endl;
        }
        //cout << "Overall GPA for dept.Name : GPA" << endl;
      } // end of the average command
      else 
      {
        cout << "**unknown command" << endl;
      }
    }

    //
    // done:
    //
    return 0;
}