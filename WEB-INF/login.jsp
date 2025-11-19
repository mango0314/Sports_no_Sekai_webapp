<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <title>Login - Sports no Sekai</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="apple-touch-icon" sizes="180x180"
	href="../assets/ico/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="../assets/ico/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../assets/ico/favicon-16x16.png">
<link rel="manifest" href="../assets/ico/site.webmanifest">

  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="fonts/icomoon/style.css">

  <link rel="stylesheet" href="css/bootstrap/bootstrap.css">
  <link rel="stylesheet" href="css/jquery-ui.css">
  <link rel="stylesheet" href="css/owl.carousel.min.css">
  <link rel="stylesheet" href="css/owl.theme.default.min.css">
  <link rel="stylesheet" href="css/owl.theme.default.min.css">

  <link rel="stylesheet" href="css/jquery.fancybox.min.css">

  <link rel="stylesheet" href="css/bootstrap-datepicker.css">

  <link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">

  <link rel="stylesheet" href="css/aos.css">

  <link rel="stylesheet" href="css/style.css">



</head>

<body>

  <!-- Navigation-->
    <!-- frame_logo.html -->
<div class="logo-header text-center py-4">
  <a href="/">
    <img src="images/Sports no Sekai logo no sfondo.png" alt="Sports no Sekai" class="logo img-fluid" />
  </a>
</div>
    

<section class="login-hero d-flex align-items-center justify-content-center">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-5">

        <h1 class="text-center mb-4 text-white">Login</h1>

        <form id="contactForm" method="post" action="Login">
          <div class="form-floating mb-3">
            <input class="form-control" id="username" name="username" type="text"
                   placeholder="Username" data-sb-validations="required" required/>
           
            <div class="invalid-feedback" data-sb-feedback="name:required">
              Username is required.
            </div>
          </div>
          <div class="form-floating mb-3">
            <input class="form-control" id="password" name="password" type="password"
                   placeholder="Password" data-sb-validations="required" required/>
          
            <div class="invalid-feedback" data-sb-feedback="email:required">
              Password is required.
            </div>
          </div>
          <button class="btn btn-primary btn-xl w-100" id="submitButton" type="submit">
            Invia
          </button>
          
            <p class="text-center mt-3">
    <small class="text-light">
      Non hai un account? 
      <a href="RichiediRegistrazione" class="fw-semibold text-decoration-underline">
        Crea il tuo account
      </a>
    </small>
  </p>
        </form>

      </div>
    </div>
  </div>
</section>




     <!-- Foot-->
        <%@ include file="foot.html" %>



  </div>
  <!-- .site-wrap -->

  <script src="js/jquery-3.3.1.min.js"></script>
  <script src="js/jquery-migrate-3.0.1.min.js"></script>
  <script src="js/jquery-ui.js"></script>
  <script src="js/popper.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/owl.carousel.min.js"></script>
  <script src="js/jquery.stellar.min.js"></script>
  <script src="js/jquery.countdown.min.js"></script>
  <script src="js/bootstrap-datepicker.min.js"></script>
  <script src="js/jquery.easing.1.3.js"></script>
  <script src="js/aos.js"></script>
  <script src="js/jquery.fancybox.min.js"></script>
  <script src="js/jquery.sticky.js"></script>
  <script src="js/jquery.mb.YTPlayer.min.js"></script>


  <script src="js/main.js"></script>

</body>

</html>