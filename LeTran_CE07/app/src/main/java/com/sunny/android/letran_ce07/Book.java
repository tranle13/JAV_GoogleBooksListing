
    // Name: Tran Le
    // JAV1 - 1808
    // File name: Book.java

package com.sunny.android.letran_ce07;

class Book {

    // Member variables
    private final String imageUrl;
    private final String bookTitle;

    // Constructor
    Book(String imageUrl, String bookTitle) {
        this.imageUrl = imageUrl;
        this.bookTitle = bookTitle;
    }

    // Getters
    String getImageUrl() {
        return imageUrl;
    }

    String getBookTitle() {
        return bookTitle;
    }
}
