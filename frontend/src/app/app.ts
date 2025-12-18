import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common'; // Importação do código
import { FormsModule } from '@angular/forms';
import { BeneficioService } from './services/beneficio.service';
import { Beneficio, TransferenciaDto } from './models/beneficio.model';

@Component({
  selector: 'app-root',
  standalone: true,
  // ADICIONE CurrencyPipe AQUI ABAIXO:
  imports: [CommonModule, FormsModule, CurrencyPipe], 
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit { // Verifique se o nome da classe é 'App' ou 'AppComponent'
  private service = inject(BeneficioService);
  
  beneficios = signal<Beneficio[]>([]);
  novo = { nome: '', descricao: '', valor: 0, ativo: true };
  transf: TransferenciaDto = { fromId: 0, toId: 0, amount: 0 };

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.service.list().subscribe(res => this.beneficios.set(res));
  }

  salvar() {
    this.service.create(this.novo).subscribe(() => {
      this.carregar();
      this.novo = { nome: '', descricao: '', valor: 0, ativo: true };
    });
  }

  // CERTIFIQUE-SE QUE O NOME É 'excluir' E NÃO 'delete'
  excluir(id: number) {
    if(confirm('Tem certeza que deseja excluir?')) {
      this.service.delete(id).subscribe(() => this.carregar());
    }
  }

  transferir() {
    this.service.transfer(this.transf).subscribe({
      next: () => {
        alert('Transferência realizada!');
        this.carregar();
      },
      error: () => alert('Erro na transferência')
    });
  }
}