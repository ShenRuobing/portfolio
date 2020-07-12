// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function getData() {
  fetch('/data').then(response => response.json()).then((data) => {
    console.log(data);
    let txt="";
    for(let i=0;i<data.quotes.length;i++)
        txt+=data.quotes[i]+"\n";
    //console.log("text is:"+txt);
    document.getElementById('quote-container').innerText = txt;
    document.getElementById('pics').innerHTML='\
    <img src="./images/1.jpg" width="345" height="288">\
    <img src="./images/2.jpg" width="275" height="288">\
    <img src="./images/4.JPG" width="225" height="288">\
    <img src="./images/11.JPG" width="424" height="300">\
    <img src="./images/8.JPG" width="424" height="300">\
    <img src="./images/9.JPG" width="424" height="576">\
    <img src="./images/10.JPG" width="424" height="576">\
    <img src="./images/5.JPG" width="280" height="400">\
    <img src="./images/6.jpg" width="280" height="400">\
    <img src="./images/7.JPG" width="283" height="400">'
  });
}

