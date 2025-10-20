import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router';

interface BossDTO {
  name: string;
  id: string;
  costInChaos: number;
  expectedValueInChaos: number;
  profitInChaos: number;
  icon: string;
}

@Component({
  selector: 'app-bosses',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './bosses.html',
  styleUrl: './bosses.css'
})
export class BossesComponent implements OnInit {
  bosses: BossDTO[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.http.get<BossDTO[]>('http://localhost:8080/api/bosses')
      .subscribe(data => this.bosses = data);
  }

  goToBoss(id: string) {
  this.router.navigate(['/bosses', id]);
}
}
