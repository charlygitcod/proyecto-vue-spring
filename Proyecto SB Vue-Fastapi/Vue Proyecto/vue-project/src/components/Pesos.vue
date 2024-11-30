<template>
  <div class="admin-section">
    <h2>Registrar Pesos</h2>
    <div class="content-container">
      <div class="form-wrapper">
        <form @submit.prevent="agregarRegistro">
          <div class="form-group">
            <label for="documento">Documento del Empleado:</label>
            <input type="number" v-model="nuevoRegistro.empleado_documento" required class="form-input" :readonly="isReadonly"/>
          </div>
          <div class="form-group">
            <label for="tipo">Tipo de Registro:</label>
            <select v-model="nuevoRegistro.tipo" required class="form-input">
              <option value="patologico">Patológico</option>
              <option value="biosanitario">Biosanitario</option>
            </select>
          </div>
          <div class="form-group">
            <label for="peso">Peso (kg):</label>
            <input type="number" step="0.01" v-model="nuevoRegistro.peso" required class="form-input"/>
          </div>
          <div class="form-group">
            <label for="turno">Turno:</label>
            <select v-model="nuevoRegistro.turno" required class="form-input">
              <option value="mañana">Mañana</option>
              <option value="tarde">Tarde</option>
              <option value="noche">Noche</option>
            </select>
          </div>
          <div class="cont-btn">
            <button type="submit" class="form-submit-button">Registrar Peso</button>
          </div>
        </form>
      </div>

      <h3>Lista de Registros</h3>
      <div class="form-group">
        <label for="filtroTurno">Filtrar por Turno:</label>
        <select v-model="filtroTurno" class="form-input">
          <option value="todos">Todos</option>
          <option value="mañana">Mañana</option>
          <option value="tarde">Tarde</option>
          <option value="noche">Noche</option>
        </select>

      </div>
      <div class="form-group">
        <label for="filtroMes">Filtrar por Mes:</label>
        <input type="month" v-model="filtroMes" class="form-input">
      </div>

      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Empleado Documento</th>
              <th>Peso (kg)</th>
              <th>Tipo</th>
              <th>Turno</th>
              <th>Fecha</th>
              <th>Hora</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="registro in registrosFiltrados" :key="registro.id_registro">
              <td>{{ registro.empleado_documento }}</td>
              <td>{{ registro.peso }} kg</td>
              <td>{{ registro.tipo }}</td>
              <td>{{ registro.turno }}</td>
              <td>{{ registro.fecha }}</td>
              <td>{{ registro.hora }}</td>
            </tr>
          </tbody>
        </table>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';

const nuevoRegistro = ref({
  empleado_documento: '',
  tipo: 'patologico',
  peso: '',
  turno: 'mañana',
});

const registros = ref([]);
const filtroTurno = ref('todos');
const filtroMes = ref('');
const documentoUsuario = ref(localStorage.getItem('nombre_usuario') || '');
const isReadonly = ref(false); 

// Computed para filtrar los registros por documento, turno y mes
const registrosFiltrados = computed(() => {
  let filtrados = registros.value;

  // Filtrar por turno
  if (filtroTurno.value !== 'todos') {
    filtrados = filtrados.filter(registro => registro.turno === filtroTurno.value);
  }

  // Filtrar por mes
  if (filtroMes.value) {
    const [añoSeleccionado, mesSeleccionado] = filtroMes.value.split('-');
    filtrados = filtrados.filter(registro => {
      const [añoRegistro, mesRegistro] = registro.fecha.split('-');
      return añoRegistro === añoSeleccionado && mesRegistro === mesSeleccionado;
    });
  }

  return filtrados;
});

const agregarRegistro = async () => {
  try {
    const { fecha, hora } = obtenerFechaHoraActual();
    const registroConFecha = {
      ...nuevoRegistro.value,
      empleado_documento: documentoUsuario.value, // Usar el documento del usuario conectado
      fecha,
      hora,
    };
    console.log("Turno seleccionado:", nuevoRegistro.value.turno);
    const response = await axios.post('http://localhost:8080/pesos/insertar', registroConFecha);
    console.log("Turno seleccionado:", nuevoRegistro.value.turno);
    console.log("Datos enviados:", registroConFecha);
    console.log("Respuesta del servidor:", response.data);
    
    Swal.fire({
      icon: 'success',
      title: 'Registro agregado con éxito',
      text: `Documento: ${documentoUsuario.value}`,
    });

    registros.value.push(response.data);
    nuevoRegistro.value = {
      empleado_documento: '',
      tipo: 'patologico',
      peso: '',
      turno: 'mañana',
    };
  } catch (error) {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Ocurrió un error al agregar el registro.',
    });
  }
};

onMounted(() => {
  const documentoGuardado = localStorage.getItem('nombre_usuario'); // Asegúrate de que este nombre sea exacto
  console.log('Documento obtenido desde localStorage:', documentoGuardado); // Depuración

  if (!documentoGuardado) {
    Swal.fire({
      icon: 'error',
      title: 'Usuario no identificado',
      text: 'No se ha encontrado un documento en localStorage',
    });
  } else {
    // Asignar al formulario el documento
    nuevoRegistro.value.empleado_documento = documentoGuardado;
    isReadonly.value = true;
    obtenerRegistros(documentoGuardado);
  }
});

const obtenerRegistros = async (documento) => {
  try {
    console.log('Obteniendo registros para el documento:', documento);
    const response = await axios.get('http://localhost:8080/pesos/consultar');
    console.log('Registros obtenidos desde la API:', response.data);

    // Asegúrate de que el valor del documento desde localStorage sea un número
    const documentoGuardado = Number(localStorage.getItem('nombre_usuario')); // Convertir a número

    // Filtrar los registros por el documento del usuario
    const registrosFiltrados = response.data.filter(
      (registro) => registro.empleado_documento === documentoGuardado
    );

    console.log('Registros filtrados:', registrosFiltrados);

    // Asignar los registros filtrados a la variable 'registros'
    registros.value = registrosFiltrados;

    if (registros.value.length === 0) {
      console.warn('No se encontraron registros para el documento:', documentoGuardado);
    }
  } catch (error) {
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'Ocurrió un error al obtener los registros.',
    });
  }
};

const obtenerFechaHoraActual = () => {
  const fecha = new Date();
  const fechaFormateada = fecha.toISOString().split('T')[0];
  const horaFormateada = fecha.toTimeString().split(' ')[0].slice(0, 5);
  return { fecha: fechaFormateada, hora: horaFormateada };
};
</script>

<style scoped>
.admin-section {
  padding: 20px;
}

.content-container {
  display: flex;
  flex-direction: column;
}

.form-wrapper {
  margin-bottom: 20px;
}

.form-group {
  text-align: left;
  margin-bottom: 15px;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-top: 5px;
}

.cont-btn {
  display: flex;
  justify-content: space-between;
}

.form-submit-button {
  background-color: #4CAF50;
  color: white;
  width: 100%;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
}

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

.download-btn {
  margin-top: 20px;
}
</style>
