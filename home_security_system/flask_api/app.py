from flask import Flask, request, jsonify
import face_recognition
import pickle

app = Flask(__name__)

with open('face_recognition\\face_encodings.pkl', 'rb') as f:
    known_face_encodings = pickle.load(f)

@app.route('/')
def index():
    return "Flask server is running!"

@app.route('/recognize', methods=['POST'])
def recognize_faces():
    file = request.files['image']
    image = face_recognition.load_image_file(file)

    face_locations = face_recognition.face_locations(image)
    face_encodings = face_recognition.face_encodings(image, face_locations)

    recognized_faces = []

    for face_encoding in face_encodings:
        matches = face_recognition.compare_faces(list(known_face_encodings.values()), face_encoding)
        name = "Unknown"

        if True in matches:
            first_match_index = matches.index(True)
            name = list(known_face_encodings.keys())[first_match_index]

        recognized_faces.append(name)

    return jsonify({"recognized_faces": recognized_faces})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)