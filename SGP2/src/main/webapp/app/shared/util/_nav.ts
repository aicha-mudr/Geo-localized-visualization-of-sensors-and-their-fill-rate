import { INavData } from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Suivi Des Données',
    url: '/',
    icon: 'fa fa-bar-chart',
  },
  {
    name: 'Poubelles',
    icon: 'icon-trash',
    children:[
      {
        name:'Liste',
        url:'/poubelle',
        icon: 'fa fa-list'
      },
      {
        name:"upload",
        url:'/upload',
        icon:'fa fa-upload'
      },
      {
        name:"Situation",
        url:'/situation',
        icon:'fa fa-line-chart'
      }
    ]
  },
  {
    name: 'Map',
    icon: 'fa fa-map',
    url:'/map'
  },
  {
    name:'Administration',
    icon: 'pi pi-fw pi-cog',
    children:[
      {
        name: 'Gestion Utilisateur',
        icon:'fa fa-user',
        url: 'admin/user-management'
      }
    ]
  }
];
