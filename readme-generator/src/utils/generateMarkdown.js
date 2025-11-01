export const generateMarkdown = (data) => {
  let markdown = '';

  // Header Section
  if (data.centered) {
    markdown += '<div align="center">\n\n';
  }

  if (data.projectName) {
    markdown += `# ${data.emoji ? data.emoji + ' ' : ''}${data.projectName}\n\n`;
  }

  if (data.subtitle) {
    markdown += `### ${data.subtitle}\n\n`;
  }

  // Badges
  if (data.badges && data.badges.length > 0) {
    data.badges.forEach(badge => {
      if (badge.name && badge.value) {
        markdown += `[![${badge.name}](https://img.shields.io/badge/${badge.name}-${badge.value}-${badge.color || 'blue'}.svg?style=flat&logo=${badge.logo || badge.name.toLowerCase()})](${badge.link || '#'})\n`;
      }
    });
    markdown += '\n';
  }

  if (data.description) {
    markdown += `${data.description}\n\n`;
  }

  // Quick Links
  if (data.quickLinks) {
    markdown += `[Features](#-features) • [Screenshots](#-screenshots) • [Tech Stack](#-tech-stack) • [Getting Started](#-getting-started)\n\n`;
  }

  if (data.centered) {
    markdown += '</div>\n\n';
  }

  markdown += '---\n\n';

  // Screenshots Section
  if (data.screenshots && data.screenshots.length > 0) {
    markdown += '## 📱 Screenshots\n\n';
    markdown += '<div align="center">\n\n';
    markdown += '| ' + data.screenshots.map(s => s.title).join(' | ') + ' |\n';
    markdown += '|' + data.screenshots.map(() => ':---:').join('|') + '|\n';
    markdown += '| ' + data.screenshots.map(s => `![${s.title}](${s.url})`).join(' | ') + ' |\n';
    markdown += '| ' + data.screenshots.map(s => s.description || '').join(' | ') + ' |\n';
    markdown += '\n</div>\n\n---\n\n';
  }

  // Features Section
  if (data.features && data.features.length > 0) {
    markdown += '## ✨ Features\n\n';
    data.features.forEach(feature => {
      markdown += `### ${feature.emoji ? feature.emoji + ' ' : ''}${feature.title}\n\n`;
      if (feature.items && feature.items.length > 0) {
        feature.items.forEach(item => {
          markdown += `- **${item.title}**\n`;
          if (item.description) {
            markdown += `  - ${item.description}\n`;
          }
        });
        markdown += '\n';
      }
    });
    markdown += '---\n\n';
  }

  // Tech Stack Section
  if (data.techStack) {
    markdown += '## 🛠️ Tech Stack\n\n';

    if (data.techStack.core && data.techStack.core.length > 0) {
      markdown += '### Core Technologies\n\n';
      markdown += '| Category | Technology |\n';
      markdown += '|----------|------------|\n';
      data.techStack.core.forEach(tech => {
        markdown += `| **${tech.category}** | ${tech.value} |\n`;
      });
      markdown += '\n';
    }

    if (data.techStack.libraries && data.techStack.libraries.length > 0) {
      markdown += '### 📚 Libraries & Frameworks\n\n';
      data.techStack.libraries.forEach(lib => {
        markdown += `#### ${lib.category}\n`;
        markdown += '```\n';
        lib.items.forEach(item => {
          markdown += `• ${item.name.padEnd(30)} - ${item.description}\n`;
        });
        markdown += '```\n\n';
      });
    }

    markdown += '---\n\n';
  }

  // Installation Section
  if (data.installation) {
    markdown += '## 🚀 Getting Started\n\n';

    if (data.installation.prerequisites) {
      markdown += '### Prerequisites\n\n';
      markdown += data.installation.prerequisites + '\n\n';
    }

    if (data.installation.steps && data.installation.steps.length > 0) {
      markdown += '### Installation\n\n';
      data.installation.steps.forEach((step, index) => {
        markdown += `${index + 1}. **${step.title}**\n`;
        if (step.code) {
          markdown += '   ```bash\n';
          markdown += `   ${step.code}\n`;
          markdown += '   ```\n\n';
        }
        if (step.description) {
          markdown += `   ${step.description}\n\n`;
        }
      });
    }

    markdown += '---\n\n';
  }

  // API Section
  if (data.api && data.api.endpoints && data.api.endpoints.length > 0) {
    markdown += '## 🌐 API Endpoints\n\n';
    markdown += '| Method | Endpoint | Description |\n';
    markdown += '|--------|----------|-------------|\n';
    data.api.endpoints.forEach(endpoint => {
      markdown += `| \`${endpoint.method}\` | \`${endpoint.path}\` | ${endpoint.description} |\n`;
    });
    markdown += '\n';

    if (data.api.sampleResponse) {
      markdown += '### Sample Response\n\n';
      markdown += '```json\n';
      markdown += data.api.sampleResponse + '\n';
      markdown += '```\n\n';
    }

    markdown += '---\n\n';
  }

  // License Section
  if (data.license) {
    markdown += '## 📄 License\n\n';
    markdown += `This project is licensed under the ${data.license} License - see the [LICENSE](LICENSE) file for details.\n\n`;
    markdown += '---\n\n';
  }

  // Footer
  if (data.footer) {
    markdown += '<div align="center">\n\n';
    markdown += '### ⭐ Star this repository if you found it helpful!\n\n';
    markdown += '**Made with ❤️**\n\n';
    markdown += '</div>\n';
  }

  return markdown;
};

// Default template data
export const defaultData = {
  projectName: 'My Awesome Project',
  emoji: '🚀',
  subtitle: 'A modern web application',
  centered: true,
  badges: [
    { name: 'Version', value: '1.0.0', color: 'blue', logo: 'github', link: '#' },
    { name: 'License', value: 'MIT', color: 'green', logo: 'opensourceinitiative', link: '#' }
  ],
  description: 'A comprehensive description of your project and what it does.',
  quickLinks: true,
  screenshots: [
    { title: 'Home', url: 'screenshots/home.png', description: 'Home page view' },
    { title: 'Features', url: 'screenshots/features.png', description: 'Features showcase' }
  ],
  features: [
    {
      emoji: '🎯',
      title: 'Core Functionality',
      items: [
        { title: 'Feature One', description: 'Description of feature one' },
        { title: 'Feature Two', description: 'Description of feature two' }
      ]
    }
  ],
  techStack: {
    core: [
      { category: 'Language', value: 'JavaScript' },
      { category: 'Framework', value: 'React' }
    ],
    libraries: [
      {
        category: 'UI Libraries',
        items: [
          { name: 'React 19.2.0', description: 'UI library' },
          { name: 'Tailwind CSS', description: 'Styling framework' }
        ]
      }
    ]
  },
  installation: {
    prerequisites: 'Node.js 18+ and npm installed',
    steps: [
      {
        title: 'Clone the repository',
        code: 'git clone https://github.com/username/project.git\ncd project',
        description: ''
      },
      {
        title: 'Install dependencies',
        code: 'npm install',
        description: ''
      },
      {
        title: 'Run the application',
        code: 'npm start',
        description: ''
      }
    ]
  },
  api: {
    endpoints: [
      { method: 'GET', path: '/api/data', description: 'Fetch all data' },
      { method: 'POST', path: '/api/data', description: 'Create new data' }
    ],
    sampleResponse: '{\n  "id": "1",\n  "name": "Sample",\n  "status": "active"\n}'
  },
  license: 'MIT',
  footer: true
};
