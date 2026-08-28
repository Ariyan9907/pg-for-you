<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Bookings - PGForYou</title>

    <link rel="stylesheet"
          href="/css/style.css">

</head>

<body>

<nav class="navbar">

    <a href="/" class="logo">
        PGForYou
    </a>

    <div>

        <a href="/" class="nav-link">
            Home
        </a>

        &nbsp;

        <a href="/bookings" class="nav-link">
            Bookings
        </a>

    </div>

</nav>


<div class="container">

    <h1 class="page-title">
        Booking History
    </h1>


    <c:choose>

        <c:when test="${not empty bookings}">

            <div class="booking-grid">

                <c:forEach var="booking"
                           items="${bookings}">

                    <div class="booking-card">

                        <h2>
                            Booking #${booking.id}
                        </h2>

                        <p>
                            <strong>Name:</strong>
                            ${booking.name}
                        </p>

                        <p>
                            <strong>Room ID:</strong>
                            ${booking.roomId}
                        </p>

                        <p>
                            <strong>Status:</strong>
                            ${booking.status}
                        </p>

                        <p>
                            <strong>Booking Date:</strong>
                            ${booking.bookingDate}
                        </p>


                        <c:if test="${booking.status != 'CANCELLED'}">

                            <form action="/bookings/${booking.id}/cancel"
                                  method="post">

                                <button type="submit"
                                        class="button">

                                    Cancel Booking

                                </button>

                            </form>

                        </c:if>

                    </div>

                </c:forEach>

            </div>

        </c:when>


        <c:otherwise>

            <p>
                No bookings found.
            </p>

        </c:otherwise>

    </c:choose>

</div>

</body>

</html>