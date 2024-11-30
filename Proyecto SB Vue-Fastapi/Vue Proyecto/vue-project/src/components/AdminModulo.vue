<template>
  <div class="admin-container">
    <h2>Administración</h2>
    <div class="menu">
      <button @click="mostrarSeccion('usuarios')">Administrar Usuarios</button>
      <button @click="mostrarSeccion('credenciales')">Administrar Credenciales</button>
    </div>
    <div class="content">
      <AdministrarUsuarios v-if="seccionActual === 'usuarios'" />
      <AdministrarCredenciales v-if="seccionActual === 'credenciales'" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Swal from 'sweetalert2';
import AdministrarUsuarios from './AdministrarUsuarios.vue';
import AdministrarCredenciales from './AdministrarCredenciales.vue';

const seccionActual = ref('usuarios');

function mostrarSeccion(seccion) {
  seccionActual.value = seccion;
  Swal.fire({
    title: 'Sección Cambiada',
    text: `Ahora estás en la sección de ${seccion}`,
    icon: 'info',
    confirmButtonText: 'OK'
  });
}
</script>

<style>



.admin-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.menu {
  text-align: center;
  margin-bottom: 20px;
}

.menu button {
  background-color: #2196F3;
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 0 10px;
  cursor: pointer;
  border-radius: 4px;
}

.menu button:hover {
  background-color: #0b7dda;
}

.content {
  width: 100%;
  max-width: 1200px; /* Ajusta el ancho máximo si es necesario */
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
