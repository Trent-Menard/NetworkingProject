<br/>
<p align="center">
  <h3 align="center">NetworkingProject</h3>

  <p align="center">
    A simple puzzle game.
    <br/>
    <br/>
    <a href="https://github.com/Trent-Menard/NetworkingProject/issues">Report Bug</a>
    <a href="https://github.com/Trent-Menard/NetworkingProject/issues">Request Feature</a>
  </p>
</p>

![Downloads](https://img.shields.io/github/downloads/Trent-Menard/NetworkingProject/total) ![Contributors](https://img.shields.io/github/contributors/Trent-Menard/NetworkingProject?color=dark-green) ![Issues](https://img.shields.io/github/issues/Trent-Menard/NetworkingProject) ![License](https://img.shields.io/github/license/Trent-Menard/NetworkingProject) 

## Table Of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Authors](#authors)
* [Acknowledgements](#acknowledgements)

## About The Project

This is my implementation of a TCP Server/Client relationship for my networking project. The goal of this is program is to demonstrate how a Client interacts with a Server.

## Built With

![Java](https://www.vectorlogo.zone/logos/java/java-icon.svg)

## Getting Started

Follow these steps to play the game (notice prerequisites).

### Prerequisites

This project was written in Java 17. Earlier versions may suffice but Java 17 is recommended. Specifically, Zulu-17 was used, available at:

* [Azul](https://www.azul.com/downloads/?version=java-17-lts&package=jdk)

### Installation

1. Download [TCPServer](../main/out/artifacts/TCPServer) & [TCPClient](../main/out/artifacts/TCPClient)
2. or Download & extract [TCPClientServerZip](../main/out/artifacts/TCPClientServerZip)

## Usage

 *Note: The following assumes using Command Prompt (Windows OS)*
1. Navigate to the directory using `cd (directory path)`.
2. Run TCPServer using `java -jar TCPServer.jar`.
3. Run TCPClient using `java -jar TCPClient.jar`.

## Known Issues

* Server's incoming Outputstream must be read by Client's Inputstream before it can send its response.<br/>As a result, the Server may have already closed the Socket (ended the game) before the Client sends its response (slight desyncronization)<br/>I've attempted to handle this by checking for this in the Client's code.
* Server cannot distinguish Client disconnection type so still prints they ran out of time.
* I didn't realize this until after making the ServerStressTest.bat but Server Client handle (ForkJoinPool) threads are limited to pc's processing power. Java Docs say default pool can be replaced but I'm unsure how to go about doing this. 

See the [open issues](https://github.com/Trent-Menard/NetworkingProject/issues) for a list of proposed features (and known issues).

## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.
* If you have suggestions for adding or removing projects, feel free to [open an issue](https://github.com/Trent-Menard/NetworkingProject/issues/new) to discuss it, or directly create a pull request after you edit the *README.md* file with necessary changes.
* Please make sure you check your spelling and grammar.
* Create individual PR for each suggestion.

### Creating A Pull Request

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See [LICENSE](https://github.com/Trent-Menard/NetworkingProject/blob/main/LICENSE) for more information.

## Authors

* **Trent Menard** - *Comp Sci Student* - [Trent Menard](https://github.com/Trent-Menard/) - *Project Creator*

## Acknowledgements

* [ShaanCoding](https://github.com/ShaanCoding/) - README generator
* [Othneil Drew](https://github.com/othneildrew/Best-README-Template) - Original README template
* [ImgShields](https://shields.io/) - Banners at the top
* [Callicoder](https://www.callicoder.com/java-8-completablefuture-tutorial/) - Completeable Future reference
* [Geeks for Geeks](https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/?ref=lbp) - Inital Socket Programming Design
