import { Roboto } from 'next/font/google';

export const roboto = Roboto({
    style: "normal",
    subsets: ["cyrillic", "cyrillic-ext", "latin"],
    weight: ["300", "400", "500", "700", "900"]
});
