import { createRouter, createWebHistory } from 'vue-router';
import LoginRegistro from '@/components/LoginRegistro.vue';
import AdminModulo from '@/components/AdminModulo.vue';
import Pesos from '@/components/Pesos.vue';
import Epp from '@/components/Epp.vue';
import PesosCoordinador from '@/components/PesosCoordinador.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: LoginRegistro
  },
  {
    path: '/ModuloAdmin',
    name: 'Administrador',
    component: AdminModulo
  },
  {
    path: '/pesos',
    name: 'Registro-pesos',
    component: Pesos
  },
  {
    path: '/epps',
    name: 'Registro-epps',
    component: Epp
  },
  {
    path: '/modcoor',
    name: 'pesos-coordinador',
    component: PesosCoordinador
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
