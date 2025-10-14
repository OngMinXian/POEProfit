import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface Cost {
  name: string;
  quantity: number;
  chaosValue: number;
  totalCost: number;
  icon: string;
}

interface Reward {
  name: string;
  probability: number;
  chaosValue: number;
  expectedValue: number;
  icon: string;
}

interface BossDTO {
  name: string;
  id: string;
  costInChaos: number;
  expectedValueInChaos: number;
  profitInChaos: number;
  costs: Cost[];
  rewards: Reward[];
}

@Component({
  selector: 'app-boss-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './boss-details.html',
  styleUrls: ['./boss-details.css'],
})
export class BossDetailComponent implements OnInit {
  boss?: BossDTO;

  constructor(private route: ActivatedRoute, private http: HttpClient, private location: Location) {}

  ngOnInit(): void {
    const bossId = this.route.snapshot.paramMap.get('id');
    if (bossId) {
      this.http.get<BossDTO>(`http://localhost:8080/api/bosses/${bossId}`)
        .subscribe(data => this.boss = data);
    }
  }
  
  // TODO: Move back to bosses pape
  goBack(): void {
    this.location.back();
  }
}
