<html>


	<head>
		<title>mYnterest</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		
		<link rel="stylesheet" href="resources/assets/css/main.css" />
		
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
		

	</head>
	

	<body class="landing2">
	

		<!-- Page Wrapper -->
			<div id="page-wrapper">
			

				

				<!-- Banner -->
					<section id="banner">
						<div class="inner">
							<h2>mYnterest</h2>
							<p>Seleziona i Topic che preferisci<br />
							<br />
							
							
							<!--  FORM LOGIN FUNZIONANTE -->
							<form action="ServletTopicsChoice" method="post">
			<div class="row uniform">

					<% String hiddenE = (String) request.getAttribute("email");
						String hiddenP = (String) request.getAttribute("password");
					%>

					<input type="hidden" name="email" value=<%=hiddenE %>>
					<input type="hidden" name="password" value=<%=hiddenP %>>


			<div class="6u 12u$(small)">

				<input type="checkbox" id="idsport" name="sport"> <label
					for="idsport">Sport</label>
			</div>

			<div class="6u 12u$(small)">

				<input type="checkbox" id="idcronaca" name="cronaca"> <label
					for="idcronaca">Cronaca</label>
			</div>
			<div class="6u 12u$(small)">
				<input type="checkbox" id="idpolitica" name="politica"> <label
					for="idpolitica">Politica</label>
			</div>
			<div class="6u 12u$(small)">
				<input type="checkbox" id="idscienze" name="scienze"> <label
					for="idscienze">Scienze</label>
			</div>
			<div class="6u 12u$(small)">
				<input type="checkbox" id="ideconomia" name="economia"> <label
					for="ideconomia">Economia</label>
			</div>
			<div class="6u 12u$(small)">
						<input type="checkbox" id="idesteri" name="esteri"> <label
							for="idesteri">Esteri</label>
							</div>
					
					<br>
					<br>
					
										
						<div class="6u$ 12u$(small)">
						<input type="checkbox" id="notifica" name="notifica"> <label
							for="notifica">Notifica via email</label>
					</div>
					
					

				</div>
				<br> <input type="submit" value="OK" class="button special" />

				

			</form>
						   
						   </div>
					</section>
					</div>
					</body>
					</html>
					