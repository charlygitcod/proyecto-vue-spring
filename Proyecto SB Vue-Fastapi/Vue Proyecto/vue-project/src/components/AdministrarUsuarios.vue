<template>
  <div class="search-container">
    <input 
      type="text" 
      v-model="searchQuery" 
      placeholder="Buscar por cédula, nombre o apellido" 
      class="search-input" 
    />
  </div>

  <div class="form-container">
    <div class="table-container">
      <h2>Lista de Usuarios</h2>
      <table>
        <thead>
          <tr>
            <th>Documento</th>
            <th>Nombre</th>
            <th>Segundo Nombre</th>
            <th>Apellido</th>
            <th>Segundo Apellido</th>
            <th>Correo</th>
            <th>Teléfono</th>
            <th>Edad</th>
            <th>Dirección</th>
            <th>Descripción</th>
            <th>Fecha Registro</th>
            <th>Rol</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="usuario in usuariosFiltrados" :key="usuario.documento">
            <td>{{ usuario.documento }}</td>
            <td>{{ usuario.nombre }}</td>
            <td>{{ usuario.segundo_nombre }}</td>
            <td>{{ usuario.apellido }}</td>
            <td>{{ usuario.segundo_apellido }}</td>
            <td>{{ usuario.correo }}</td>
            <td>{{ usuario.telefono }}</td>
            <td>{{ usuario.edad }}</td>
            <td>{{ usuario.direccion }}</td>
            <td>{{ usuario.descripcion }}</td>
            <td>{{ usuario.fecha_registro }}</td>
            <td>{{ usuario.rol }}</td>
            <td>
              <div class="actions-container">
                <button @click="editarUsuario(usuario)" class="edit-btn">Editar</button>
                <button @click="eliminarUsuario(usuario.documento)" class="delete-btn">Eliminar</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="form-wrapper">
      <h1>{{ editModeUsuario ? 'Editar Usuario' : 'Agregar Usuario' }}</h1>
      <form @submit.prevent="guardarUsuario">
        <div class="form-group">
          <label for="documento">Documento</label>
          <input type="number" id="documento" v-model="documento" placeholder="Documento" required class="form-input" :disabled="editModeUsuario" />
        </div>
        <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input type="text" id="nombre" v-model="nombre" placeholder="Nombre" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="segundo_nombre">Segundo Nombre:</label>
          <input type="text" id="segundo_nombre" v-model="segundo_nombre" placeholder="Segundo Nombre" class="form-input" />
        </div>
        <div class="form-group">
          <label for="apellido">Apellido:</label>
          <input type="text" id="apellido" v-model="apellido" placeholder="Apellido" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="segundo_apellido">Segundo Apellido:</label>
          <input type="text" id="segundo_apellido" v-model="segundo_apellido" placeholder="Segundo Apellido" class="form-input" />
        </div>
        <div class="form-group">
          <label for="correo">Correo:</label>
          <input type="email" id="correo" v-model="correo" placeholder="Correo" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="telefono">Teléfono:</label>
          <input type="text" id="telefono" v-model="telefono" placeholder="Teléfono" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="edad">Edad:</label>
          <input type="number" id="edad" v-model="edad" placeholder="Edad" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="direccion">Dirección:</label>
          <input type="text" id="direccion" v-model="direccion" placeholder="Dirección" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="descripcion">Descripción:</label>
          <input type="text" id="descripcion" v-model="descripcion" placeholder="Descripción" required class="form-input" />
        </div>
        <div class="form-group">
          <label for="rol">Rol:</label>
          <select id="rol" v-model="rol" required class="form-select">
            <option value="administrador">Administrador</option>
            <option value="operario">Operario</option>
            <option value="coordinador">Coordinador</option>
            <option value="usuario_epp">Usuario EPP</option>
          </select>
        </div>

        <div class="cont-btn">
          <button type="submit" class="form-submit-button">{{ editModeUsuario ? 'Actualizar' : 'Agregar' }}</button>
          <button type="button" @click="resetUsuario" class="form-toggle-button">{{ editModeUsuario ? 'Cancelar Edición' : 'Limpiar Formulario' }}</button>
          <p v-if="errorMessage" class="form-error">{{ errorMessage }}</p>
          <p v-if="successMessage" class="form-success">{{ successMessage }}</p>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';

const searchQuery = ref('');
const usuarios = ref([]);
const usuariosFiltrados = ref([]);
const editModeUsuario = ref(false);
const successMessage = ref('');
const errorMessage = ref('');
const documento = ref('');
const nombre = ref('');
const segundo_nombre = ref('');
const apellido = ref('');
const segundo_apellido = ref('');
const correo = ref('');
const telefono = ref('');
const edad = ref('');
const direccion = ref('');
const descripcion = ref('');
const rol = ref('administrador');

const obtenerUsuarios = async () => {
  try {
    const response = await axios.get('http://localhost:8080/usuarios/consultarusuarios');
    usuarios.value = response.data;
    usuariosFiltrados.value = usuarios.value; // Inicia con todos los usuarios
  } catch (error) {
    await Swal.fire({
      title: 'Error',
      text: 'Error al obtener la lista de usuarios',
      icon: 'error',
      confirmButtonText: 'Aceptar',
    });
  }
};

// Filtrar usuarios dinámicamente según lo que el usuario escriba
watch(searchQuery, (nuevoValor) => {
  if (nuevoValor === '') {
    usuariosFiltrados.value = usuarios.value; // Si no hay búsqueda, mostrar todos los usuarios
  } else {
    usuariosFiltrados.value = usuarios.value.filter(usuario => 
      usuario.documento.toString().includes(nuevoValor) ||
      usuario.nombre.toLowerCase().includes(nuevoValor.toLowerCase()) ||
      usuario.apellido.toLowerCase().includes(nuevoValor.toLowerCase())
    );
  }
});

const guardarUsuario = async () => {
  const usuario = {
    documento: documento.value,
    nombre: nombre.value,
    segundo_nombre: segundo_nombre.value,
    apellido: apellido.value,
    segundo_apellido: segundo_apellido.value,
    correo: correo.value,
    telefono: telefono.value,
    edad: edad.value,
    direccion: direccion.value,
    descripcion: descripcion.value,
    fecha_registro: new Date().toISOString().split('T')[0],
    rol: rol.value,
  };

  try {
    if (editModeUsuario.value) {
      await axios.put(`http://localhost:8080/usuarios/modificarusuario/${usuario.documento}`, usuario);
      await Swal.fire({
        title: 'Éxito',
        text: 'Usuario actualizado exitosamente',
        icon: 'success',
        confirmButtonText: 'Aceptar',
      });
    } else {
      await axios.post('http://localhost:8080/usuarios/insertarusuario', usuario);
      await Swal.fire({
        title: 'Éxito',
        text: 'Usuario agregado exitosamente',
        icon: 'success',
        confirmButtonText: 'Aceptar',
      });
    }
    obtenerUsuarios();
    resetUsuario();
  } catch (error) {
    await Swal.fire({
      title: 'Error',
      text: 'Error al guardar el usuario',
      icon: 'error',
      confirmButtonText: 'Aceptar',
    });
  }
};

const editarUsuario = (usuario) => {
  documento.value = usuario.documento;
  nombre.value = usuario.nombre;
  segundo_nombre.value = usuario.segundo_nombre;
  apellido.value = usuario.apellido;
  segundo_apellido.value = usuario.segundo_apellido;
  correo.value = usuario.correo;
  telefono.value = usuario.telefono;
  edad.value = usuario.edad;
  direccion.value = usuario.direccion;
  descripcion.value = usuario.descripcion;
  rol.value = usuario.rol;
  editModeUsuario.value = true;
};

const eliminarUsuario = async (documento) => {
  try {
    await axios.delete(`http://localhost:8080/usuarios/eliminarusuario/${documento}`);
    await Swal.fire({
      title: 'Éxito',
      text: 'Usuario eliminado exitosamente',
      icon: 'success',
      confirmButtonText: 'Aceptar',
    });
    obtenerUsuarios();
  } catch (error) {
    await Swal.fire({
      title: 'Error',
      text: 'Error al eliminar el usuario',
      icon: 'error',
      confirmButtonText: 'Aceptar',
    });
  }
};

const resetUsuario = () => {
  documento.value = '';
  nombre.value = '';
  segundo_nombre.value = '';
  apellido.value = '';
  segundo_apellido.value = '';
  correo.value = '';
  telefono.value = '';
  edad.value = '';
  direccion.value = '';
  descripcion.value = '';
  rol.value = 'administrador';
  editModeUsuario.value = false;
  successMessage.value = '';
  errorMessage.value = '';
};

onMounted(() => {
  obtenerUsuarios();
});
</script>

<style scoped>
.search-container {
  display: flex; 
  align-items: center; 
  justify-content: center;
  gap: 10px; 
  margin: 20px 0;
  margin-top: 50px;
  margin-bottom: -30px;
}

.search-input {
  height: 30px;
  padding: 5px;
  font-size: 16px; 
  width: 400px; 
  border: 2px solid #ccc; 
  border-radius: 5px;
  outline: none;
  transition: border-color 0.3s ease;
}

.search-input:focus {
  border-color: #5c8df8; 
}

.search-btn {
  padding: 10px 20px; 
  font-size: 16px; 
  background-color: #5c8df8; 
  color: white;
  border: none; 
  border-radius: 5px; 
  cursor: pointer; 
  transition: background-color 0.3s ease; 
}

.search-btn:hover {
  background-color: #4a7df2; 
}


.table-container {
  max-width: 100%;
  margin-top: 800px;
  margin-bottom: 50px;

  
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

.form-wrapper{
  width: 100%;
}

.form-container {
  display: flex;
  flex-direction: column;
}

.form-group {
  text-align: left;
  margin-bottom: 15px;
}

.form-input, .form-select {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.cont-btn{
  display: flex;
  justify-content: space-around;
}

.form-submit-button, .form-toggle-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
}



.form-toggle-button {
  background-color: #f44336;
  width: 100%;
  margin-left: 10px;
}

.form-error, .form-success {
  color: red;
}
</style>
