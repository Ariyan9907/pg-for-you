<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Register - PGForYou</title>

    <link rel="stylesheet"
          href="/css/style.css">

</head>

<body>

<div class="container">

    <h1>Create Account</h1>

    <form action="/register" method="post">

        <div>

            <label>Name</label>

            <input type="text"
                   name="name"
                   required>

        </div>

        <br>

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
            Register
        </button>

    </form>

    <br>

    <p>
        Already have an account?
        <a href="/login">
            Login
        </a>
    </p>

</div>

</body>

</html>