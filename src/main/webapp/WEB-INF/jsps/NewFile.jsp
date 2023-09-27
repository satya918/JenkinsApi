<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        h1 {
            color: #333;
        }

        form {
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            max-width: 400px;
            margin: 0 auto;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        button[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 3px;
            padding: 10px 20px;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Create Jenkins Pipeline</h1>
    <form method="POST" action="/createPipeline">
        <label for="pipelineName">Pipeline Name:</label>
        <input type="text" id="pipelineName" name="pipelineName" required><br><br>
        
        <label for="pipelineDescription">Pipeline Description:</label>
        <input type="text" id="pipelineDescription" name="pipelineDescription"><br><br>
        
        <label for="gitRepoUrl">Git Repository URL:</label>
        <input type="text" id="gitRepoUrl" name="gitRepoUrl" required><br><br>
        
        <label for="gitBranch">Git Branch:</label>
        <input type="text" id="gitBranch" name="gitBranch" required><br><br>
        
        <label for="jenkinsfilePath">Jenkinsfile Path:</label>
        <input type="text" id="jenkinsfilePath" name="jenkinsfilePath" required><br><br>
        
        <button type="submit">Create Pipeline</button>
    </form>
</body>

</html>