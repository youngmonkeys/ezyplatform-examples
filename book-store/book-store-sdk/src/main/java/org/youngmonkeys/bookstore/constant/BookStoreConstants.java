/*
 * Copyright 2024 youngmonkeys.org
 * 
 * Licensed under the ezyplatform, Version 1.0.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://youngmonkeys.org/licenses/ezyplatform-1.0.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.youngmonkeys.bookstore.constant;

public final class BookStoreConstants {

    public static final String CATEGORY_NAME_HIGHLIGHT_BOOK =
        "highlight-book";
    public static final String CATEGORY_NAME_NEW_BOOK = "new-book";
    public static final String CATEGORY_NAME_BESTSELLING_BOOK =
        "bestselling-book";

    public static final int DEFAULT_BOOKS_LIMIT = 8;

    public static final String ROLE_AUTHOR_NAME = "book_author";
    public static final String ROLE_AUTHOR_DISPLAY_NAME = "Book Author";

    public static final String SETTING_NAME_BOOK_AUTHOR_ROLE_ID =
        "book_store_book_author_role_id";

    private BookStoreConstants() {}
}
