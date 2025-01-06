@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page
<!-- src/main/jte/courses/show.jte -->

<!-- Для наглядности мы не стали включать сюда основную структуру HTML -->
<main>
    <h1>${page.getCourse().getName()}</h1>// интерполяция – указание Java-кода внутри ${}
    <p>${page.getCourse().getDescription()}</p>// интерполяция
</main>