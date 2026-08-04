# Definitions

* **Page Fragment**: A reusable portion of a page. A page fragment can be included in multiple pages.
* **Page**: A complete page. A page may contain multiple page fragments.
* **Mail Template**: A reusable email template (subject + body) sent by EzyMail. A mail template can contain placeholder variables that are substituted with real values when the mail is sent or previewed.
* **Content Template**: A reusable template (title + body) managed by EzySupport, grouped under a free-form `template_type` (for example `ticket-reply`, `notification`, `canned-response`). A content template can contain placeholder variables that are substituted with real values when it is used or previewed.
* **Term**: A taxonomy entry (category, tag, etc.) managed by EzyArticle, grouped under a free-form `term_type` (for example `category`, `tag`). Terms are used to classify pages and posts.

---

# Platform

This project is built on:

EzyPlatform – EzyArticle

---

# Technologies Used

The system supports the following technologies:

* **Thymeleaf** – Similar to HTML, but includes template attributes that allow EzyPlatform to render dynamic HTML.
* **HTML**
* **JavaScript**
* **CSS**

---

# Directory and File Structure

## `page-fragments` Directory

This directory contains reusable page fragments.

Example:

```
page-fragments/common/footer
```

* `common` → `page_name`
* `footer` → `fragment_name`

Inside each `fragment_name` directory (e.g., `footer`), there are three files:

* **`content.html`**
  Contains the main HTML content of the fragment.

* **`head.html`**
  This section will be injected inside the `<head>` tag of the final page.
  You may use:

  * `<title>`
  * `<meta>`
  * `<link>`
  * `<style>`
  * `<script>`
  * `<base>`

  ⚠ Avoid placing complex JavaScript logic here.

* **`foot.html`**
  This section will be placed inside the `<body>` tag, right before the closing `</body>` tag.
  You may use:

  * HTML tags
  * `<script>` tags

* **`meta.json`**

  This is the file that contains the metadata of the page fragment, such as the title, content type, and status, for example:

  ```json
  {
    "title": "Common header fragment",
    "contentType": "HTML",
    "status": "DRAFT"
  }
  ```

---

## `pages` Directory

This directory contains full pages.

Example:

```
pages/home
```

* `home` → page `slug`

Each page directory contains:

* **`content.html`**
  Contains the main HTML structure of the page.
  You should only import `content` regions from page fragments here.

* **`head.html`**
  Injected inside the `<head>` tag.
  You may use:

  * `<title>`
  * `<meta>`
  * `<link>`
  * `<style>`
  * `<script>`
  * `<base>`

  ⚠ Avoid placing complex JavaScript logic here.
  You should only import `head` regions from page fragments here.

* **`foot.html`**
  Injected before the closing `</body>` tag.
  You may use HTML or `<script>` tags here.
  You should only import `foot` regions from page fragments here.

* **`meta.json`**

  This is the file that contains the metadata of the page fragment, such as the slug, title, summary, pageType, content type, and status, for example:

  ```json
  {
    "slug": "example-page",
    "title": "Example Page",
    "summary": "",
    "featuredImageName": "",
    "pageType": "PAGE",
    "contentType": "HTML",
    "status": "DRAFT",
    "metadata": {}
  }
  ```

  The available page types include:

  1. **PAGE**: A standard page that shares the same header and footer with other pages. Put page body HTML in `content.html`, page-specific head markup or CSS in `head.html`, and page-specific JavaScript in `foot.html`.
  2. **STANDALONE_PAGE**: An independent blank page that does not share the header and footer with other pages. You must provide the full HTML, CSS, and JavaScript in `content.html` only. Do not generate code into `head.html` or `foot.html`.
  3. **TEMPLATED_PAGE**: A page that shares the header and footer with other pages but allows you to dynamically set the page meta, for example:

---

## `posts` Directory

This directory contains blog posts.

Example:

```
posts/hello-world
```

* `hello-world` → post `slug`

Each post directory contains the same files as a page:

* **`content.html`**
  Contains the main HTML content of the post.

* **`head.html`**
  Injected inside the `<head>` tag. Same rules as for pages.

* **`foot.html`**
  Injected before the closing `</body>` tag. Same rules as for pages.

* **`meta.json`**

  This is the file that contains the metadata of the post, such as the slug, title, summary, postType, content type, and status, for example:

  ```json
  {
    "slug": "hello-world",
    "title": "Hello World",
    "summary": "",
    "featuredImageName": "",
    "postType": "POST",
    "contentType": "HTML",
    "status": "DRAFT",
    "metadata": {}
  }
  ```

  Unlike pages, `postType` for a post is normally `POST`.

---

## `api/v1/media` Directory

This directory stores media assets.

* Media files inside this directory are synchronized to the server.
* When a media file is overwritten, it will be re-synchronized.
* However, browsers and servers often cache media files.

⚠ If you update a media file, you should append a version parameter to force reload:

```
/api/v1/media/image.png?v=timestamp
```

Example:

```
/api/v1/media/logo.png?v=1736483930
```

For CSS and JavaScript files in `/api/v1/media`, use `ezy:mhref` and `ezy:msrc` so EzyArticle can automatically append the latest update timestamp as the version parameter. This helps clients avoid using cached old versions.

Examples:

```html
<link rel="stylesheet" ezy:mhref="/api/v1/media/style.css" />
<script ezy:msrc="/api/v1/media/app.js"></script>
```

**Note**: The media name must match the expression `^[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)*$`, which means it may only contain English letters, numbers, dots (`.`), hyphens (`-`), or underscores (`_`), example `hello-world.png`. If any character does not meet this condition, it will automatically be replaced with a random character.

---

## `mail-templates` Directory

This directory contains EzyMail templates used to send emails (welcome emails, password reset, order confirmation, notifications, etc.). This feature requires the EzyMail plugin to be installed on the server.

Example:

```
mail-templates/welcome-email
```

* `welcome-email` → `template_name`

Each `template_name` directory contains three files:

* **`title.html`**
  The mail subject template.

* **`content.html`**
  The mail body template.

* **`meta.json`**

  This is the file that contains the metadata of the mail template: id, content type, status, and sample values for its placeholder variables, for example:

  ```json
  {
    "contentType": "HTML",
    "status": "DRAFT",
    "parameters": {
      "userName": "John",
      "activationLink": "https://example.com/activate/123"
    }
  }
  ```

  Available content types: `HTML`, `THYMELEAF`, `TEXT`, `MARKDOWN`, `JSON`, `JAVASCRIPT`. Use `HTML` unless the mail needs Thymeleaf expressions, in which case use `THYMELEAF`.

  `parameters` is not sent to the server; it only exists locally so the **Preview** command can substitute sample values for the placeholder variables below. If left out, it defaults to `{}`.

### Placeholder Variables

Both `title.html` and `content.html` may reference variables using either syntax:

* `${variableName}`
* `{{variableName}}`

Example:

```html
<p>Hi ${userName}, please confirm your email by clicking <a href="${activationLink}">here</a>.</p>
```

When generating or editing a mail template, prefer one of these two placeholder syntaxes so the variables are picked up automatically by the preview screen. Add a sample value for every variable you introduce to `meta.json`'s `parameters` field so the preview renders meaningful content instead of empty placeholders.

To create a new mail template, create the folder structure above under `mail-templates`. If it already exists on the server, sync its content as described in the Command Palette section below instead of guessing its current `title.html` / `content.html` / `meta.json`.

---

## `content-templates` Directory

This directory contains EzySupport content templates (ticket replies, notifications, canned responses, etc.), grouped by type. This feature requires the EzySupport plugin to be installed on the server.

Example:

```
content-templates/ticket-reply/welcome-reply
```

* `ticket-reply` → `template_type`
* `welcome-reply` → `template_name`

`template_type` is a free-form label, not a fixed enum — the server accepts any string. It is just a folder used to group related templates (for example `ticket-reply`, `notification`, `canned-response`, `auto-reply`). When asked to create a new content template and no type is specified, pick a short kebab-case name that describes what the template is for, reuse an existing `template_type` folder under `content-templates` if one already fits, and ask the user only if it is genuinely ambiguous.

Each `template_name` directory contains three files:

* **`title.html`**
  The template title.

* **`content.html`**
  The template body.

* **`meta.json`**

  This is the file that contains the metadata of the content template: id, content type, status, and sample values for its placeholder variables, for example:

  ```json
  {
    "contentType": "HTML",
    "status": "DRAFT",
    "parameters": {
      "userName": "John"
    }
  }
  ```

  Available content types: `HTML`, `THYMELEAF`, `TEXT`, `MARKDOWN`, `JSON`, `JAVASCRIPT`. Use `HTML` unless the template needs Thymeleaf expressions, in which case use `THYMELEAF`.

  `parameters` is not sent to the server; it only exists locally so the **Preview** command can substitute sample values for placeholder variables. The server also never returns this field when syncing, so your local values are always preserved. If left out, it defaults to `{}`.

You may reference variables in `title.html` and `content.html` using `${variableName}` or `{{variableName}}`, the same as mail templates.

To create a new content template, create the folder structure above under `content-templates/<template_type>`. If it already exists on the server, sync its content as described in the Command Palette section below instead of guessing its current `title.html` / `content.html` / `meta.json`.

---

## `terms` Directory

This directory contains EzyArticle terms (categories, tags, etc.) used to classify pages and posts, grouped by type.

Example:

```
terms/category/announcements
```

* `category` → `term_type`
* `announcements` → `term_slug`

`term_type` is a free-form label, not a fixed enum — the server accepts any string. It is just a folder used to group related terms (for example `category`, `tag`). When asked to create a new term and no type is specified, pick a short kebab-case name that describes what the term is for, reuse an existing `term_type` folder under `terms` if one already fits, and ask the user only if it is genuinely ambiguous.

Each `term_slug` directory contains two files:

* **`description.txt`**
  The term description.

* **`meta.json`**

  This is the file that contains the metadata of the term: id, name, featured image name, display order, and status, for example:

  ```json
  {
    "id": 0,
    "name": "Announcements",
    "featuredImageName": "",
    "displayOrder": 0,
    "status": "DRAFT"
  }
  ```

  `name` is the human-readable display name shown to end users (in menus, breadcrumbs, term listings, etc.), so never copy `term_slug` into it verbatim. Derive a friendly name from the slug: split on `-`/`_`, capitalize each word, and expand obvious abbreviations, for example `term_slug` `women-shoes` → `"Women Shoes"`, `mens-t-shirts` → `"Men's T-Shirts"`, `faq` → `"FAQ"`. When the user gives you an explicit display name, use that instead of deriving one from the slug.

If the term already exists on the server, sync its content as described in the Command Palette section below instead of guessing its current `description.txt` / `meta.json`.

---

## `<target>/messages` Directory (I18n)

This directory contains i18n message files (Java `.properties` format) used by EzySupport. This requires the EzySupport plugin.

Example:

```
admin/messages/messages.properties
admin/messages/messages_vi.properties
web/messages/messages.properties
```

* `target` → either `admin` or `web`.
* `messages.properties` → the default message file (no language suffix).
* `messages_<lang>.properties` → the message file for language `<lang>` (for example `messages_vi.properties`, `messages_en.properties`). Parse the file name to get `lang`.

Each file is a standard Java `.properties` file, one `key=value` message per line, for example:

```properties
common.hello=Hello
common.goodbye=Goodbye
```

⚠ Always write entries in the standard `key=value` form (no spaces around `=`) when generating or editing these files. The key and value are trimmed before being published, so `key = value` still works, but do not rely on that — write `key=value` directly.

To create a new message file, create the folder structure above under `admin/messages` or `web/messages`.

---

# EzyArticle Custom Tags

## `<ezy:block>`

This is a self-closing custom tag that can be used in both page fragments and pages.

It supports the following attributes:

---

### `ezy:utext`

Renders dynamic Thymeleaf or HTML content.

Example:

```html
<ezy:block ezy:utext="${pageFragments.get('content').additionalHead}" />
```

In this example:

* `pageFragments.get('content').additionalHead` returns Thymeleaf or HTML code.
* If the returned value contains Thymeleaf syntax, it will continue rendering until final HTML is produced.

---

### `ezy:replace`

Imports a specific region from a page fragment.

Syntax:

```html
<ezy:block ezy:replace="~{page_name/fragment_name :: region_name}" />
```

Where:

* `page_name` → Directory inside `page-fragments`
* `fragment_name` → Fragment folder inside `page_name`
* `region_name` → One of:

  * `content`
  * `head`
  * `foot`

These correspond to:

* `content.html`
* `head.html`
* `foot.html`

---

### Example Usage

```html
<ezy:block ezy:replace="~{search/active :: content}" />
<ezy:block ezy:replace="~{search/active :: head}" />
<ezy:block ezy:replace="~{search/active :: foot}" />
```

⚠ Important rule:

* `content` fragments must be placed inside the page's `content.html`
* `head` fragments must be placed inside the page's `head.html`
* `foot` fragments must be placed inside the page's `foot.html`

Example for page `home`:

**pages/home/content.html**

```html
<ezy:block ezy:replace="~{search/active :: content}" />
```

**pages/home/head.html**

```html
<ezy:block ezy:replace="~{search/active :: head}" />
```

**pages/home/foot.html**

```html
<ezy:block ezy:replace="~{search/active :: foot}" />
```

---

# Command Palette & API

You can trigger the same synchronization flows from the **Command Palette** (`Ctrl+Shift+P` / `Cmd+Shift+P`) by running:

### Available Commands

* **EzyArticle: Sync Page Fragments** (`ezyarticle.syncFragments`)
* **EzyArticle: Sync Page Fragment Folder** (`ezyarticle.syncFragmentFolder`)
* **EzyArticle: Sync Page Fragment File** (`ezyarticle.syncFragmentFile`)
* **EzyArticle: Sync Page Folder** (`ezyarticle.syncPageFolder`)
* **EzyArticle: Sync Page File** (`ezyarticle.syncPageFile`)
* **EzyArticle: Sync Post Folder** (`ezyarticle.syncPostFolder`)
* **EzyArticle: Sync Post File** (`ezyarticle.syncPostFile`)
* **EzyArticle: Sync Mail Template Folder** (`ezyarticle.syncMailTemplateFolder`)
* **EzyArticle: Refresh Mail Template File** (`ezyarticle.syncMailTemplateFile`)
* **EzyArticle: Sync Content Template Folder** (`ezyarticle.syncContentTemplateFolder`)
* **EzyArticle: Refresh Content Template File** (`ezyarticle.syncContentTemplateFile`)
* **EzyArticle: Sync Term Folder** (`ezyarticle.syncTermFolder`)
* **EzyArticle: Refresh Term File** (`ezyarticle.syncTermFile`)
* **EzyArticle: Publish Content To Server** (`ezyarticle.publishContent`) – Also publishes mail templates under `mail-templates`, content templates under `content-templates`, terms under `terms`, and i18n message files under `admin/messages` or `web/messages`.
* **EzyArticle: Preview Page** (`ezyarticle.previewPage`)
* **EzyArticle: Preview Mail Template** (`ezyarticle.previewMailTemplate`)
* **EzyArticle: Preview Content Template** (`ezyarticle.previewContentTemplate`)
* **EzyArticle: Create Project** (`ezyarticle.createProject`) – Scaffold `environment.json` and bootstrap the workspace.

---

## Using the Extension API

Automation scripts or other extensions can call any command programmatically using:

```ts
await vscode.commands.executeCommand('ezyarticle.<commandName>');
```

Example:

```ts
await vscode.commands.executeCommand('ezyarticle.syncFragments');
```

---

## Notes

All commands:

* Require a valid `environment.json`
* Respect the `environmentReadyForSync` guard
* Show confirmation dialogs before overwriting files or publishing content

---


# EzyArticle Custom Functions

## `ezyfunctions.call`

This function allows calling backend-provided functions from a page or fragment.

Due to security restrictions, only specific functions are available.

Example: Retrieve a post by `slug`

```html
<th:block th:with="slug=${ezyfunctions.call('get_request_parameter', '{name:'\'' + 'slug' + '\''}')},
          story=${ezyfunctions.call('get_post_by_slug', '{slug:'\'' + #strings.escapeJavaScript(slug) + '\''}')}">
```

In this example:

* `get_request_parameter` retrieves the `slug` parameter from the request.
* `get_post_by_slug` fetches the corresponding post from the backend.
* `#strings.escapeJavaScript()` ensures the slug is safely escaped before being used.

---

# Best Practices

* Keep logic separation clear:

  * `head` → metadata and light setup
  * `content` → structure and layout
  * `foot` → scripts and dynamic behavior
* Avoid heavy JavaScript logic inside `head.html`.
* Always match fragment regions correctly (`content` → `content`, `head` → `head`, `foot` → `foot`).
* Use version parameters for media updates to prevent caching issues.
* Escape dynamic parameters before passing them into backend calls.
