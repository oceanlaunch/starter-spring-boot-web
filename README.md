# Spring Boot web app starter (Thymeleaf)

A task list with server-rendered pages, in Spring Boot 4, Thymeleaf and Java 21, ready to deploy on
[OceanLaunch](https://oceanlaunch.cloud).

- `TaskController.java`: the pages and the form, with validation messages
- `src/main/resources/templates/index.html`: the page (Thymeleaf)
- `src/main/resources/static/css/app.css`: its styles (light and dark mode)

Tasks are kept in memory, so the list starts empty after each deploy. To keep them, add a database (see the
Spring Boot + PostgreSQL starter).

## Deploy it

In the OceanLaunch portal, open **Templates** and choose **Spring Boot web app**. Your app is live at its
`*.apps.oceanlaunch.cloud` address in a few minutes.

## Make it yours

1. On GitHub, choose **Use this template** to get your own copy of this repository.
2. In the portal, open your app, and under **Make it yours** give your new repository's address.
3. Change the code and push: every push to `main` deploys a new version.

## Run it on your computer

```bash
mvn spring-boot:run
```

Then open http://localhost:8080. Run the tests with `mvn test`.
