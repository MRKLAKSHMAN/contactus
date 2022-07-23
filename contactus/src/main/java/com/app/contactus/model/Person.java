package com.app.contactus.model;

public class Person {

        private String userId;
        private String fullName;
        private String lastName;
        private String password;

        public Person() {

        }

        @Override
        public String toString() {
                return "Person{" +
                        "userId='" + userId + '\'' +
                        ", fullName='" + fullName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", password='" + password + '\'' +
                        '}';
        }

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}