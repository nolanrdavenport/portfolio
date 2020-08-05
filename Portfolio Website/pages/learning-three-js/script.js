import * as THREE from './node_modules/three/build/three.module.js';
import { GLTFLoader } from './GLTFLoader.js';

var canvas = document.getElementById("backgroundCanvas");

var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
camera.position.z = -10;
var renderer = new THREE.WebGLRenderer({
    antialias: true,
    canvas: canvas,
    alpha: true
});

renderer.setClearColor(0x000000, 0);
renderer.setSize(window.innerWidth, window.innerHeight);
//document.body.appendChild(renderer.domElement);

var ambientLight = new THREE.AmbientLight(0x404040, 1); // soft white light
scene.add(ambientLight);

var d_1 = new THREE.DirectionalLight(0xffffff, 1);
d_1.position.set(100, 100, 100);
scene.add(d_1);

var d_2 = new THREE.DirectionalLight(0xffffff, 1);
d_2.position.set(100, 100, -100);
scene.add(d_2);

var loader = new GLTFLoader();


var monkey;
loader.load('./pages/learning-three-js/models/comm.glb', function(gltf) {
    monkey = gltf.scene;
    monkey.scale.set(0.6, 0.6, 0.6);

    monkey.traverse((o) => {
        if (o.isMesh) {
            o.material.color = new THREE.Color(0x009900);
            o.material.emissive = new THREE.Color(0x00ffff);
            o.material.wireframe = true;
        }
    });

    scene.add(monkey);

}, undefined, function(error) {

    console.error(error);

});

camera.position.z = 5;
camera.position.y = 2;

window.addEventListener('resize', onWindowResize, false);

var animate = function() {
    requestAnimationFrame(animate);

    monkey.rotation.y += 0.01;
    renderer.render(scene, camera);
};

function onWindowResize() {

    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();

    renderer.setSize(window.innerWidth, window.innerHeight);

    render();

}

animate();