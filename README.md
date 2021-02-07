# PrivateLibrary-server
PrivateLibrary is a final application for a JAVA programming subject at my university. The goal was to create a desktop application that would allow the user to manage his own library. To use the application, the user must register and log in. The app allows you to add books, authors, publishers and genres. In addition, it provides the ability to view books added by other users.

<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#tools-used">Tools Used</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About The Project

![Main Page](/images/01.png)

PrivateLibrary-server is a backend application that allows you to connect the frontend part of PrivateLibrary with a database. It uses Java and the MySQL language. The application uses mysql-connector-java to connect to the database. In addition, unit tests were performed in the JUnit 5 technology.

### Built With
* [Java](https://www.java.com/pl/)
* [MySQL](https://www.mysql.com/)
* [JUnit 5](https://junit.org/junit5/)

### Tools Used
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [XAMPP](https://www.apachefriends.org/pl/index.html)

## Getting Started
At the very beginning, you need to create a database in MySQL ([privlib_mysql.sql](https://github.com/PawelPabianczyk/PrivateLibrary-server/blob/main/database/privlib_mysql.sql) file in the repository). You can use any IDE that supports MySQL (e.g. PHPMyAdmin) or directly via the MySQL console. Then clone the repository and run it in the selected Java-enabled IDE or using the console.

## Usage

The running application should display the following in the console: "Server up & ready for connections...".

To use the application fully, you must download the frontend application [PrivateLibrary-client](https://github.com/PawelPabianczyk/PrivateLibrary-client).

## Contributing

Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Contact

Email - pawel.pabianczyk1999@gmail.com

LinkedIn - [Pawel Pabianczyk](https://www.linkedin.com/in/pawe%C5%82-pabia%C5%84czyk-a32693171/)

Project Link: [https://github.com/PawelPabianczyk/PrivateLibrary-client](https://github.com/PawelPabianczyk/PrivateLibrary-client)
