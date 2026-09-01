<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Login - PGForYou</title>

    <link rel="stylesheet"
          href="/css/style.css">

</head>

<body>

<div class="container">

    <h1>Login</h1>

    <form action="/login"
          method="post">

        <div>

            <label>Email</label>

            <input type="email"
                   name="email"
                   required>

        </div>

        <br>

        <div>

            <label>Password</label>

            <input type="password"
                   name="password"
                   required>

        </div>

        <br>

        <button type="submit"
                class="button">
            Login
        </button>

    </form>

    <br>

    <p>
        Don't have an account?
        <a href="/register">
            Register
        </a>
    </p>

</div>

</body>

</html>