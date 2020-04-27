/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.associationtable;

/**
 * Association Table Mapping pattern saves an association as a table
 * with foreign keys to the tables that are linked by the association
 * (many-to-many relationship).
 *
 * <p>The below example demonstrates a many-to-many relationship
 * between Student and Course tables.
 */
public class App {

  /**
  * Program entry point.
  *
  * @param args command line args
  */
  public static void main(String[] args) {

    /* Create Student table and Course table */
    StudentTableImpl studentTable = new StudentTableImpl();
    CourseTableImpl courseTable = new CourseTableImpl();

    /* Create a student and a course */
    Student student1 = new Student("Matthew");
    Course course1 = new Course("IT");

    /* Add the objects to tables where they belong */
    studentTable.insert(student1);
    courseTable.insert(course1);

    /* Add the student to the course */
    course1.addStudent(student1);

  }

  private App() {
  }

}
