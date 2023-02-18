export class Personamodel {
    id!: number;
    nombre: string;
    apellido: string;
    email: string;
    imagen: string;

    constructor(nombre: string, apellido: string, email: string, imagen: string) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.imagen = imagen;
    }
}
