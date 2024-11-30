<template>
  <div class="admin-section">
    <h3>Gestión de Credenciales</h3>
    <div class="content-container">
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Empleado Documento</th>
              <th>Contraseña</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="credencial in credenciales" :key="credencial.empleado_documento">
              <td>{{ credencial.empleado_documento }}</td>
              <td>{{ credencial.password }}</td>
              <td>
                <div class="actions-container">
                  <button @click="editarCredencial(credencial)" class="edit-btn">Editar</button>
                  <button @click="eliminarCredencial(credencial.empleado_documento)" class="delete-btn">Eliminar</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <!-- Formulario para agregar/editar credenciales -->
      <div class="form-wrapper">
        <h1>{{ editModeCredencial ? 'Editar Credencial' : 'Agregar Credencial' }}</h1>
        <form @submit.prevent="guardarCredencial">
          <div class="form-group">
            <label for="empleado_documento">Empleado Documento</label>
            <input type="text" id="empleado_documento" v-model="empleado_documento" placeholder="Documento" required class="form-input" :disabled="editModeCredencial" />
          </div>
          <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" v-model="password" placeholder="Contraseña" required class="form-input" />
          </div>
          <div class="cont-btn">
            <button type="submit" class="form-submit-button">{{ editModeCredencial ? 'Actualizar' : 'Agregar' }}</button>
            <button type="button" @click="resetCredencial" class="form-toggle-button">{{ editModeCredencial ? 'Cancelar Edición' : 'Limpiar Formulario' }}</button>
          </div>
          <p v-if="errorMessage" class="form-error">{{ errorMessage }}</p>
          <p v-if="successMessage" class="form-success">{{ successMessage }}</p>
        </form>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';

// Variables reactivas para credenciales
const empleado_documento = ref('');
const password = ref('');
const credenciales = ref([]);
const editModeCredencial = ref(false);
const successMessage = ref('');
const errorMessage = ref('');

// Función para guardar o actualizar credencial
const guardarCredencial = async () => {
  const credencial = {
    empleado_documento: empleado_documento.value,
    password: password.value,
  };

  try {
    if (editModeCredencial.value) {
      await axios.put(`http://localhost:8080/credenciales/modificarcredencial/${empleado_documento.value}`, credencial);
      await Swal.fire({
        title: 'Éxito',
        text: 'Credencial actualizada exitosamente',
        icon: 'success',
        confirmButtonText: 'Aceptar',
      });
    } else {
      await axios.post('http://localhost:8080/credenciales/insertarcredencial', credencial);
      await Swal.fire({
        title: 'Éxito',
        text: 'Credencial agregada exitosamente',
        icon: 'success',
        confirmButtonText: 'Aceptar',
      });
    }
    obtenerCredenciales();
    resetCredencial();
  } catch (error) {
    await Swal.fire({
      title: 'Error',
      text: error.response.data.detail || 'Error al guardar la credencial',
      icon: 'error',
      confirmButtonText: 'Aceptar',
    });
  }
};

// Función para obtener la lista de credenciales
const obtenerCredenciales = async () => {
  try {
    const response = await axios.get('http://localhost:8080/credenciales/consultarcredenciales');
    credenciales.value = response.data;
  } catch (error) {
    await Swal.fire({
      title: 'Error',
      text: 'Error al obtener la lista de credenciales',
      icon: 'error',
      confirmButtonText: 'Aceptar',
    });
  }
};

// Función para eliminar credencial
const eliminarCredencial = async (empleado_documento) => {
  try {
    const result = await Swal.fire({
      title: '¿Estás seguro?',
      text: '¡No podrás recuperar esta credencial!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
    });

    if (result.isConfirmed) {
      console.log(empleado_documento);
      
      await axios.delete(`http://localhost:8080/credenciales/eliminarcredencial/${empleado_documento}`);
      await Swal.fire({
        title: 'Eliminado',
        text: 'Credencial eliminada exitosamente',
        icon: 'success',
        confirmButtonText: 'Aceptar',
      });
      obtenerCredenciales();
    }
  } catch (error) {
    await Swal.fire({
      title: 'Error',
      text: 'Error al eliminar la credencial',
      icon: 'error',
      confirmButtonText: 'Aceptar',
    });
  }
};

// Función para editar credencial
const editarCredencial = (credencial) => {
  empleado_documento.value = credencial.empleado_documento;
  password.value = credencial.password;
  editModeCredencial.value = true;
};

// Función para resetear el formulario de credenciales
const resetCredencial = () => {
  empleado_documento.value = '';
  password.value = '';
  editModeCredencial.value = false;
  successMessage.value = '';
  errorMessage.value = '';
};

// Obtener la lista de credenciales al montar el componente
onMounted(obtenerCredenciales);
</script>
<style scoped>
.table-container {
  width: 100%;
  margin-top: 20px;
}

table {
  width: 100%;
  background-color: antiquewhite;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

.actions-container {
  display: flex;
  gap: 10px;
}

.edit-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
}

.delete-btn {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
}

.form-wrapper {
  margin-top: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-input {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.cont-btn{
  display: flex;
}

.form-submit-button, .form-toggle-button {
  background-color: #4CAF50;
  color: white;
  width: 50%;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.form-toggle-button {
  background-color: #f44336;
  margin-left: 10px;
}

.form-error, .form-success {
  color: red;
}
</style>
