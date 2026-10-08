import { CardItem } from "@components/CardItem";

interface CardsGridProps {
	maxItems: number;
}

export function CardsGrid({ maxItems }: CardsGridProps) {
	const mockAtributes = [
		{ atributeLabel: "Atributo Prueba", atributeValue: "1234" },
		{ atributeLabel: "Atributo Prueba", atributeValue: "12345" },
		{ atributeLabel: "Atributo Prueba", atributeValue: "12346" },
	];
	return (
		<section className="grid w-full grid-cols-3 items-center gap-8 p-6">
			<CardItem
				href="#"
				itemId="123"
				itemName="Prueba"
				itemAtributes={mockAtributes}
			/>
			<CardItem
				href="#"
				itemId="123"
				itemName="Prueba"
				itemAtributes={mockAtributes}
			/>
			<CardItem
				href="#"
				itemId="123"
				itemName="Prueba"
				itemAtributes={mockAtributes}
			/>
			<CardItem
				href="#"
				itemId="123"
				itemName="Prueba"
				itemAtributes={mockAtributes}
			/>
			<CardItem
				href="#"
				itemId="123"
				itemName="Prueba"
				itemAtributes={mockAtributes}
			/>
			<CardItem
				href="#"
				itemId="123"
				itemName="Prueba"
				itemAtributes={mockAtributes}
			/>
		</section>
	);
}
