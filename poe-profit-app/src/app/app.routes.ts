import { Routes } from '@angular/router';
import { BossesComponent } from './bosses/bosses';
import { BossDetailComponent } from './boss-details/boss-details';

export const routes: Routes = [
    { path: '', redirectTo: 'bosses', pathMatch: 'full' },
    { path: 'bosses', component: BossesComponent },
    { path: 'bosses/:id', component: BossDetailComponent }
];
