package com.nighthawk.spring_portfolio.mvc.calendar;

/**
 * Simple POJO
 * Used to Interface with APCalendar
 * The toString method(s) prepares object for JSON serialization
 * Note... this is NOT an entity, just an abstraction
 */
class Year {
   private int year;
   private boolean isLeapYear;
   private int dayOfYear;
   private int firstDayOfYear;

   // zero argument constructor
   public Year() {
   }

   /* year getter/setters */
   public int getYear() {
      return year;
   }

   public void setYear(int year) {
      this.year = year;
      this.setIsLeapYear(year);
   }

   /* isLeapYear getter/setters */
   public boolean getIsLeapYear(int year) {
      return APCalendar.isLeapYear(year);
   }

   private void setIsLeapYear(int year) { // this is private to avoid tampering
      this.isLeapYear = APCalendar.isLeapYear(year);
   }

   /* isLeapYearToString formatted to be mapped to JSON */
   public String isLeapYearToString() {
      return ("{ \"year\": " + this.year + ", " + "\"isLeapYear\": " + this.isLeapYear + " }");
   }

   // day of year getters and setters
   public int getDayOfYear(int day, int month, int year) {
      return APCalendar.dayOfYear(month, day, year);
   }

   public void setDayOfYear(int day, int month, int year) {
      this.dayOfYear = APCalendar.dayOfYear(month, day, year);
   }

   // dayOfYearToString formatted to be mapped to JSON
   public String dayOfYearToString() {
      return (("{ \"year\": " + this.year + ", " + "\"dayOfYear\": " + this.dayOfYear + " }"));
   }

   // day of year getters and setters
   public int getFirstDayOfYear(int year) {
      return APCalendar.firstDayOfYear(year);
   }

   public void setFirstDayOfYear(int year) {
      this.firstDayOfYear = APCalendar.firstDayOfYear(year);
   }

   // dayOfYearToString formatted to be mapped to JSON
   public String firstDayOfYearToString() {
      return (("{ \"year\": " + this.year + ", " + "\"firstDayOfYear\": " + this.firstDayOfYear + " }"));
   }

   /* standard toString placeholder until class is extended */
   public String toString() {
      return isLeapYearToString();
   }

   public static void main(String[] args) {
      Year year = new Year();
      year.setYear(2022);
      System.out.println(year);
   }
}