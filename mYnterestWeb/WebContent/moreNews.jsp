<!DOCTYPE HTML>


<%@ page import="java.util.*"%>
<%@ page import="server.FeedMessage"%>

<html>
<head>
<title>MY NEWS</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="resources/assets/css/main.css" />

</head>
<body>

	<!-- Page Wrapper -->
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header">

			<nav id="nav">
				<ul>
					<li class="special"><a href="#menu" class="menuToggle"><span>Menu</span></a>
						<div id="menu">



							<form action="ServletLogout" method="get">
								<fieldset>
									<input type="submit" value="Log Out" class="button special" />

								</fieldset>
							</form>


							<form action="ServletOptions" method="post">
								<fieldset>
									<br> <input type="submit" value="Options"
										class="button special" />
									<% String hidden = (String) request.getAttribute("email"); %>
									<input type="hidden" name="email" value=<%=hidden %>>
								</fieldset>

							</form>
						</div></li>
				</ul>
			</nav>
		</header>

		<!-- Main -->

		<jsp:useBean id="x" class="beans.NewsView" scope="session" />

		<%
						String topic = (String) request.getAttribute("topic");
				%>

		<article id="main">
			<header>
				<h2>My News</h2>
				<p>
					Tutte le tue notizie di
					<%=topic %>
				</p>
			</header>


			<section class="wrapper style5">
				<div class="inner">


					<% 							

						ArrayList<FeedMessage> fm = x.getNews(topic,true);

								

								for (FeedMessage fmj : fm) {

														

									//TITOLO
									out.println("<h5>" + fmj.getTitle() + "</h5>");

									//DESCRIZIONE

									out.println("<p>" + fmj.getDescription() + "</p>");

									//DATA E SORGENTE

									out.println(fmj.getPubDate() + "   " + fmj.getSource());

									//LINK

									out.println("<a href=\"" + fmj.getLink() + "   \">Vai alla notizia completa</a><br>");
									
									out.println("<hr />");

								}
																			
					%>

				</div>
			</section>
		</article>



	</div>

	<!-- Scripts -->
	<script src="resources/assets/js/jquery.min.js"></script>
	<script src="resources/assets/js/jquery.scrollex.min.js"></script>
	<script src="resources/assets/js/jquery.scrolly.min.js"></script>
	<script src="resources/assets/js/skel.min.js"></script>
	<script src="resources/assets/js/util.js"></script>
	<script src="resources/assets/js/main.js"></script>

</body>
</html>