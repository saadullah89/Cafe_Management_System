<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voice to Text</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            text-align: center;
        }

        h1 {
            color: #333;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
            margin: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, color 0.3s ease;
            outline: none;
        }

        button#start {
            background-color: #28a745;
            color: white;
        }

        button#start.active, button#start.clicked {
            background-color: black;
            cursor: not-allowed;
        }

        button#stop {
            background-color: #007bff;
            color: white;
        }

        button#stop:active {
            background-color: black;
        }

        button#clear {
            background-color: #dc3545;
            color: white;
        }

        button#clear:active {
            background-color: black;
        }

        button#copy {
            background-color: #ffc107;
            color: white;
        }

        button#copy.clicked {
            background-color: black;
            cursor: not-allowed;
        }

        #output-box {
            margin: 20px auto;
            width: 80%;
            max-width: 600px;
            background: white;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }

        #output {
            font-size: 18px;
            color: #555;
            min-height: 50px;
            white-space: pre-wrap;
            text-align: left;
        }
    </style>
</head>
<body>
    <h1>Voice to Text (Urdu)</h1>
    <button id="start">Start Recording</button>
    <button id="stop" disabled>Stop Recording</button>
    <button id="clear">Clear</button>
    <button id="copy">Copy Text</button>
    <div id="output-box">
        <p id="output">Your text will appear here...</p>
    </div>

    <script>
        const startButton = document.getElementById('start');
        const stopButton = document.getElementById('stop');
        const clearButton = document.getElementById('clear');
        const copyButton = document.getElementById('copy');
        const output = document.getElementById('output');

        const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

        if (SpeechRecognition) {
            const recognition = new SpeechRecognition();
            recognition.lang = 'ur-PK';
            recognition.continuous = true;
            recognition.interimResults = true;

            let isRecording = false;
            let finalTranscript = '';

            startButton.addEventListener('click', () => {
                recognition.start();
                isRecording = true;
                startButton.classList.add('clicked');
                startButton.disabled = true;
                stopButton.disabled = false;
                console.log("Recording started...");
            });

            stopButton.addEventListener('click', () => {
                recognition.stop();
                isRecording = false;
                startButton.classList.remove('clicked');
                startButton.disabled = false;
                stopButton.disabled = true;
                console.log("Recording stopped.");
            });

            recognition.onresult = (event) => {
                let interimTranscript = '';
                for (let i = event.resultIndex; i < event.results.length; i++) {
                    const transcript = event.results[i][0].transcript;
                    if (event.results[i].isFinal) {
                        finalTranscript += transcript + ' ';
                    } else {
                        interimTranscript += transcript;
                    }
                }
                output.textContent = finalTranscript + interimTranscript;
            };

            recognition.onend = () => {
                if (isRecording) {
                    recognition.start();
                }
            };

            recognition.onerror = (event) => {
                console.error("Error occurred in recognition: ", event.error);
                isRecording = false;
                startButton.disabled = false;
                stopButton.disabled = true;
            };
        } else {
            alert('Your browser does not support Web Speech API. Please use a modern browser like Chrome.');
        }

        clearButton.addEventListener('click', () => {
            output.textContent = "Your text will appear here...";
            finalTranscript = '';
            console.log("Text cleared.");
        });

        copyButton.addEventListener('click', () => {
            navigator.clipboard.writeText(output.textContent).then(() => {
                // Disable the button temporarily after copy
                copyButton.classList.add('clicked');
                copyButton.disabled = true;
                console.log("Text copied to clipboard.");
                
                // Re-enable the button after a short delay
                setTimeout(() => {
                    copyButton.classList.remove('clicked');
                    copyButton.disabled = false;
                }, 200); // 2 seconds delay before re-enabling the button
            }).catch(err => {
                console.error("Failed to copy text: ", err);
            });
        });
    </script>
</body>
</html>
