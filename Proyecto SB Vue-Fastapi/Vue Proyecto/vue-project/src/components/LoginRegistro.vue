<template>
  <div class="form-container">
    <div class="form-wrapper">
      <!-- Logo de la aplicación -->
      <img src="C:\Users\Carlos\Desktop\INS VIV\Proyecto Final\Proyecto SB Vue-Fastapi\Vue Proyecto\vue-project\src\components\icons\favicon-ecoentornos-1920w.png" alt="Logo de la App" class="logo" />
      
      <!-- Nombre de la aplicación -->
      <h1 class="app-name">EcoEntorno</h1>

      <h2>Iniciar sesión</h2>
      <form @submit.prevent="loginUsuario">
        <div class="form-group">
          <label for="nombreUsuario">Nombre de Usuario:</label>
          <input type="number" id="nombreUsuario" v-model="nombreUsuario" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="password">Contraseña:</label>
          <input type="password" id="password" v-model="password" required class="form-input" />
        </div>
        <button type="submit" class="form-submit-button">Iniciar sesión</button>
        <p v-if="menError" class="form-error">{{ menError }}</p>
        <p v-if="menExito" class="form-success">{{ menExito }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import Swal from 'sweetalert2';
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const nombreUsuario = ref('');
const password = ref('');
const menError = ref('');
const menExito = ref('');

const loginUsuario = async () => {
  try {
    // Enviar solicitud POST al backend para autenticar al usuario
    const response = await axios.post('http://localhost:8080/credenciales/login', {
      nombre_usuario: nombreUsuario.value,
      password: password.value,
    });

    const { rol, nombre_usuario } = response.data;

    // Guardar el nombre de usuario en localStorage
    localStorage.setItem('nombre_usuario', nombreUsuario.value);
    console.log('Usuario guardado en localStorage: ', localStorage.getItem('nombre_usuario'));

    // Redirigir según el rol del usuario
    if (rol === 'administrador') {
      router.push('/ModuloAdmin');
    } else if (rol === 'operario') {
      router.push('/pesos');
    } else if (rol === 'usuario_epp') {
      router.push('/epps');
    } else if (rol === 'coordinador') {
      router.push('/modcoor');
    } else {
      throw new Error('Rol no válido');
    }

    // Mostrar mensaje de éxito
    menExito.value = 'Inicio de sesión exitoso';
    Swal.fire({
      icon: 'success',
      title: 'Inicio de Sesión',
      text: 'Inicio de sesión exitoso',
    });

    // Limpiar mensaje de éxito después de 3 segundos
    setTimeout(() => {
      menExito.value = '';
    }, 3000);
  } catch (error) {
    console.error('Error al iniciar sesión', error);
    menError.value = 'Error al iniciar sesión. Verifique sus credenciales.';
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Error al iniciar sesión',
    });

    // Limpiar mensaje de error después de 3 segundos
    setTimeout(() => {
      menError.value = '';
    }, 3000);
  }
};
</script>

<style>
body {
  background: linear-gradient(to right, #e18dfa, #9acff3);
}

.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.form-wrapper {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.logo {
  width: 100px;
  height: auto;
}

.app-name {
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.form-submit-button {
  background-color: #007bff;
  color: white;
  padding: 10px;
  border: none;
  cursor: pointer;
  width: 100%;
}

.form-error {
  color: red;
}

.form-success {
  color: green;
}
</style>
