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
          <!-- Campos del formulario -->
          <div class="form-group" v-for="field in formFields" :key="field.id">
            <label :for="field.id">{{ field.label }}</label>
            <input 
              v-if="field.type !== 'select'"
              :type="field.type"
              :id="field.id"
              v-model="field.value"
              :placeholder="field.placeholder"
              :required="field.required"
              :disabled="field.disabled"
              class="form-input"
            />
            <select 
              v-if="field.type === 'select'" 
              :id="field.id" 
              v-model="field.value" 
              :required="field.required" 
              class="form-select"
            >
              <option v-for="option in field.options" :value="option" :key="option">{{ option }}</option>
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
  const formFields = ref([
    { id: 'documento', label: 'Documento', type: 'number', value: '', required: true, disabled: false },
    { id: 'nombre', label: 'Nombre', type: 'text', value: '', required: true, disabled: false },
    { id: 'segundo_nombre', label: 'Segundo Nombre', type: 'text', value: '', required: false, disabled: false },
    { id: 'apellido', label: 'Apellido', type: 'text', value: '', required: true, disabled: false },
    { id: 'segundo_apellido', label: 'Segundo Apellido', type: 'text', value: '', required: false, disabled: false },
    { id: 'correo', label: 'Correo', type: 'email', value: '', required: true, disabled: false },
    { id: 'telefono', label: 'Teléfono', type: 'text', value: '', required: true, disabled: false },
    { id: 'edad', label: 'Edad', type: 'number', value: '', required: true, disabled: false },
    { id: 'direccion', label: 'Dirección', type: 'text', value: '', required: true, disabled: false },
    { id: 'descripcion', label: 'Descripción', type: 'text', value: '', required: true, disabled: false },
    { id: 'rol', label: 'Rol', type: 'select', value: 'administrador', required: true, options: ['administrador', 'operario', 'coordinador', 'usuario_epp'] }
  ]);
  
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
      documento: formFields.value[0].value,
      nombre: formFields.value[1].value,
      segundo_nombre: formFields.value[2].value,
      apellido: formFields.value[3].value,
      segundo_apellido: formFields.value[4].value,
      correo: formFields.value[5].value,
      telefono: formFields.value[6].value,
      edad: formFields.value[7].value,
      direccion: formFields.value[8].value,
      descripcion: formFields.value[9].value,
      fecha_registro: new Date().toISOString().split('T')[0],
      rol: formFields.value[10].value,
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
    formFields.value.forEach(field => {
      field.value = usuario[field.id];
    });
    editModeUsuario.value = true;
  };
  
  const resetUsuario = () => {
    formFields.value.forEach(field => field.value = '');
    editModeUsuario.value = false;
    successMessage.value = '';
    errorMessage.value = '';
  };
  
  onMounted(obtenerUsuarios);
  </script>
  
  <style scoped>
  /* Aquí va el estilo existente que ya tienes */
  </style>
  